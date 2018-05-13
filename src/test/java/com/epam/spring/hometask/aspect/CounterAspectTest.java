package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.domain.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class CounterAspectTest {

    @Autowired
    EventDao eventDao;

    @Autowired
    CounterAspect counterAspect;

    @Test
    public void countOfGetByNameMethod() {
        Event event1 = new Event();
        event1.setName("First");

        Event event2 = new Event();
        event2.setName("Second");

        eventDao.getByName("First");
        eventDao.getByName("Second");

        System.out.println(counterAspect.getCounterByName());


    }

    @Test
    public void countOfGetPriceMethod() {
    }

    @Test
    public void countOfBookingMethod() {
    }
}