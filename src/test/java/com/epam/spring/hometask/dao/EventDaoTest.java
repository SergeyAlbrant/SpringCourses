package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class EventDaoTest {

    @Autowired
    private EventDao eventDao;

    private static final String FIRST_TEST_NAME = "Titanic";
    private static final double FIRST_BASE_PRICE = 12;
    private static final EventRating FIRST_RATING = EventRating.HIGH;
    private static final String SECOND_TEST_NAME = "Avatar";
    private static final double SECOND_BASE_PRICE = 23;
    private static final EventRating SECOND_RATING = EventRating.LOW;
    private static final LocalDateTime DATETIME_NOW = LocalDateTime.now();
    private static final LocalDate DATE_NOW = LocalDate.now();

    @Before
    public void setUp() {
        Event event = buildEvent(null, FIRST_TEST_NAME, FIRST_BASE_PRICE, FIRST_RATING);
        event.addAirDateTime(DATETIME_NOW);
        event.addAirDateTime(DATETIME_NOW.plusDays(1));
        event.addAirDateTime(DATETIME_NOW.plusDays(2));
        eventDao.save(event);

        event = buildEvent(null, SECOND_TEST_NAME, SECOND_BASE_PRICE, SECOND_RATING);
        event.addAirDateTime(DATETIME_NOW.minusDays(1));
        event.addAirDateTime(DATETIME_NOW.plusDays(3));
        eventDao.save(event);
    }

    private Event buildEvent(Long id, String name, double basePrice, EventRating rating) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setBasePrice(basePrice);
        event.setRating(rating);
        return event;
    }


    @Test
    public void getByName() {
        Event eventByName = eventDao.getByName(FIRST_TEST_NAME);
        assertNotNull(eventByName);
        assertNotNull(eventByName.getName());
        assertNotNull(eventByName.getAirDates());
        assertNotNull(eventByName.getRating());
        assertNotNull(eventByName.getAuditoriums());

        assertThat(eventByName.getName(), is(FIRST_TEST_NAME));
        assertThat(eventByName.getAirDates().size(), is(3));
        assertThat(eventByName.getRating(), is(FIRST_RATING));
        assertThat(eventByName.getBasePrice(), is(FIRST_BASE_PRICE));
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void getForDateRange() {
        Set<Event> eventsForDateRange = eventDao.getForDateRange(DATE_NOW, DATE_NOW.plusDays(4));

        assertNotNull(eventsForDateRange);
        assertThat(eventsForDateRange, hasSize(2));

        eventsForDateRange.forEach(event -> {
            assertNotNull(event);
            assertNotNull(event.getName());
            assertNotNull(event.getAirDates());
            assertNotNull(event.getRating());
            assertNotNull(event.getAuditoriums());
        });
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void getNextEvents() {
        Set<Event> nextEventsForTwoDays = eventDao.getNextEvents(DATETIME_NOW.plusDays(2));

        assertNotNull(nextEventsForTwoDays);
        assertThat(nextEventsForTwoDays, hasSize(1));

        nextEventsForTwoDays.forEach(event -> {
            assertNotNull(event);
            assertNotNull(event.getName());
            assertNotNull(event.getAirDates());
            assertNotNull(event.getRating());
            assertNotNull(event.getAuditoriums());
        });
    }
}