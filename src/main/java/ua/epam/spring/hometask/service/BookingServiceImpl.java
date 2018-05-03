package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

    private EventDao eventDao;
    private DiscountService discountService;

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {

        // TODO: 03-May-18 add discount calculation
        // TODO: 03-May-18 check if booked
        final double basePrice = event.getBasePrice();
        final EventRating rating = event.getRating();
        //final byte discount = discountService.getDiscount(user, event, dateTime, seats.size());
        final Auditorium auditorium = event.getAuditoriums().get(dateTime);
        final Set<Long> allSeats = auditorium.getAllSeats();
        final Set<Long> vipSeats = auditorium.getVipSeats();

        if (!allSeats.containsAll(seats)) {
            throw new IllegalArgumentException("Some of these seats are not exist");
        }

        double ticketsPrice = seats.stream()
                                   .mapToDouble(seat -> vipSeats.contains(seat) ? basePrice * 2 : basePrice)
                                   .sum();


        return rating.equals(EventRating.HIGH) ? ticketsPrice * 1.2 : ticketsPrice;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return null;
    }
}
