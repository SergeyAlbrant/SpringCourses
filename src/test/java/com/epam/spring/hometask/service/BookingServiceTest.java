package com.epam.spring.hometask.service;

import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class BookingServiceTest {

    @Autowired
    private AuditoriumDao auditoriumDao;
    @Autowired
    private BookingService bookingService;

    private Event event;
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void setUp() {
        event = new Event();
        event.setBasePrice(20);
        event.setRating(EventRating.HIGH);
        event.addAirDateTime(NOW, auditoriumDao.getByName("VIP"));
    }


    @Test
    public void getTicketsPrice() {
        double ticketsPrice = bookingService.getTicketsPrice(event, NOW, null, new HashSet<>(Arrays.asList(1L, 2L)));
        assertTrue(ticketsPrice > 0);
    }
}