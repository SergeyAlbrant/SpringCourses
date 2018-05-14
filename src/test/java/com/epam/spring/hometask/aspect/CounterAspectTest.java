package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.EventService;
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
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class CounterAspectTest {

    @Autowired
    EventDao eventDao;
    @Autowired
    EventService eventService;
    @Autowired
    BookingService bookingService;
    @Autowired
    AuditoriumDao auditoriumDao;
    @Autowired
    CounterAspect counterAspect;

    public static final LocalDateTime NOW = LocalDateTime.now();
    public static final String FIRST_NAME = "First";
    public static final String SECOND_NAME = "Second";

    private Event event1;
    private Event event2;
    private Ticket ticket1;
    private Ticket ticket2;

    @Before
    public void initEvent() {
        event1 = new Event();
        event1.setName(FIRST_NAME);
        event1.setBasePrice(10);
        event1.setRating(EventRating.HIGH);
        event1.addAirDateTime(NOW, auditoriumDao.getByName("VIP"));

        eventDao.save(event1);

        event2 = new Event();
        event2.setName(SECOND_NAME);
        event2.setBasePrice(20);
        event2.setRating(EventRating.MID);
        event2.addAirDateTime(NOW, auditoriumDao.getByName("VIP"));
        eventDao.save(event2);

        ticket1 = new Ticket(null, event1, NOW, 1);
        ticket2 = new Ticket(null, event2, NOW, 2);
    }

    @Test
    public void countOfGetByNameMethod() {

        eventService.getByName(FIRST_NAME);
        eventService.getByName(FIRST_NAME);
        eventService.getByName(SECOND_NAME);

        Map<Event, Long> counterByName = counterAspect.getCounterByName();

        assertNotNull(counterByName);
        assertThat(counterByName.size(), is(2));
        assertThat(counterByName.get(event1), is(2L));
        assertThat(counterByName.get(event2), is(1L));
    }

    @Test
    public void countOfGetPriceMethod() {
        bookingService.getTicketsPrice(event1, NOW, null, new HashSet<>(Arrays.asList(1L)));
        bookingService.getTicketsPrice(event2, NOW, null, new HashSet<>(Arrays.asList(2L)));

        Map<Event, Long> counterByPrice = counterAspect.getCounterByPrice();

        assertNotNull(counterByPrice);
        assertThat(counterByPrice.size(), is(2));
        assertThat(counterByPrice.get(event1), is(1L));
        assertThat(counterByPrice.get(event2), is(1L));
    }

    @Test
    public void countOfBookingMethod() {
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket1)));
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket2)));

        Map<Event, Long> counterByBooking = counterAspect.getCounterByBooking();

        assertNotNull(counterByBooking);
        assertThat(counterByBooking.size(), is(2));
        assertThat(counterByBooking.get(event1), is(1L));
        assertThat(counterByBooking.get(event2), is(1L));
    }
}