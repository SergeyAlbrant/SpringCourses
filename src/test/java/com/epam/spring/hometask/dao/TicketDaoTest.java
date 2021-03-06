package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import com.epam.spring.hometask.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class TicketDaoTest {

    private static final Event TEST_EVENT = new Event();
    private static final Event ANOTHER_EVENT = new Event();

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    @Qualifier("mapTicketDao")
    private TicketDao ticketDao;

    @Before
    public void setUp() {
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 1));
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 2));
        ticketDao.save(buildTicket(null, TEST_EVENT, NOW, 3));
        ticketDao.save(buildTicket(null, ANOTHER_EVENT, NOW.minusDays(3), 3));
    }

    private Ticket buildTicket(User user, Event event, LocalDateTime dateTime, long seat) {
        return new Ticket(user, event, dateTime, seat);
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