package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class TicketDaoTest {

    private static final Event TEST_EVENT = new Event();
    private static final Event ANOTHER_EVENT = new Event();

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private TicketDao ticketDao;

    @Before
    public void setUp() {
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 1));
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 2));
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 3));
        ticketDao.save(buildTicket(null, ANOTHER_EVENT, NOW.minusDays(3), 3));
    }

    private Ticket buildTicket(User user, Event event, LocalDateTime dateTime, long seat) {
        Ticket ticket = new Ticket(user, event, dateTime, seat);
        return ticket;
    }


    @Test
    public void getAllByEventAndDate() {
        Set<Ticket> allTicketByEventAndDate = ticketDao.getAllByEventAndDate(TEST_EVENT, NOW);

        assertNotNull(allTicketByEventAndDate);
        assertThat(allTicketByEventAndDate, hasSize(3));

        allTicketByEventAndDate.forEach(ticket -> {
            assertNotNull(ticket);
            assertNotNull(ticket.getDateTime());
            assertNotNull(ticket.getEvent());
            assertNotNull(ticket.getSeat());
        });
    }
}