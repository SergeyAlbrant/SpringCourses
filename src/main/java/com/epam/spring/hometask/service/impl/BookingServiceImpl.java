package com.epam.spring.hometask.service.impl;

import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.dao.TicketDao;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookingServiceImpl implements BookingService {

    private EventDao eventDao;
    private TicketDao ticketDao;
    private DiscountService discountService;

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {

        final double basePrice = event.getBasePrice();
        final byte discount = (byte) (discountService.getDiscount(user, event, dateTime, seats.size()) / 100);
        final EventRating rating = event.getRating();
        final Auditorium auditorium = event.getAuditoriums().get(dateTime);
        final Set<Long> allSeats = auditorium.getAllSeats();
        final Set<Long> vipSeats = auditorium.getVipSeats();
        final Set<Long> bookedSeats = getPurchasedTicketsForEvent(event, dateTime).stream()
                .map(Ticket::getSeat)
                .collect(Collectors.toSet());

        if (!allSeats.containsAll(seats)) {
            throw new IllegalArgumentException("Some of these seats are not exist");
        }

        if (seats.stream().anyMatch(bookedSeats::contains)) {
            throw new IllegalArgumentException("Some of these seats are booked");
        }

        double ticketsPrice = seats.stream()
                .mapToDouble(seat -> vipSeats.contains(seat) ? basePrice * 2 : basePrice)
                .sum();

        if (discount > 0) {
            ticketsPrice *= discount;
        }
        return rating.equals(EventRating.HIGH) ? ticketsPrice * 1.2 : ticketsPrice;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
            if (getPurchasedTicketsForEvent(ticket.getEvent(), ticket.getDateTime()).contains(ticket)) {
                throw new IllegalArgumentException("Some of these seats are booked");
            }
            ticketDao.save(ticket);
            User user = ticket.getUser();
            if (user != null)
                user.getTickets().add(ticket);
        });
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDao.getAllByEventAndDate(event, dateTime);
    }
}
