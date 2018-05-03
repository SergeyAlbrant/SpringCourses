package ua.epam.spring.hometask.service.impl;

import lombok.Getter;
import lombok.Setter;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

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


        return rating.equals(EventRating.HIGH) ? discount * ticketsPrice * 1.2 : discount * ticketsPrice;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
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
