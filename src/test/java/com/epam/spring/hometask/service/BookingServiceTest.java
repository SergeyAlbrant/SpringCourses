package com.epam.spring.hometask.service;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class BookingServiceTest {

    @Autowired
    private AuditoriumDao auditoriumDao;
    @Autowired
    private BookingService bookingService;

    private Event event;
    private Ticket ticket;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void setUp() {
        event = new Event();
        event.setBasePrice(20);
        event.setRating(EventRating.HIGH);
        event.addAirDateTime(NOW, auditoriumDao.getByName("VIP"));

        ticket = new Ticket(null , event, NOW, 1);
    }


    @Test
    public void getTicketsPrice() {
        double ticketsPrice = bookingService.getTicketsPrice(event, NOW, null, new HashSet<>(Arrays.asList(1L, 2L)));
        assertTrue(ticketsPrice > 0);
    }

    @Test
    public void bookTickets() {
        assertFalse(bookingService.getPurchasedTicketsForEvent(event, NOW).contains(ticket));
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        assertTrue(bookingService.getPurchasedTicketsForEvent(event, NOW).contains(ticket));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionWhenBookingTicketsThatAlreadyBooked() {
        assertFalse(bookingService.getPurchasedTicketsForEvent(event, NOW).contains(ticket));
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
    }
}