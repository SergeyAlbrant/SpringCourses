package com.epam.spring.hometask.service;

import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.impl.DiscountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class DiscountServiceTest {


    @Autowired
    private DiscountService discountService;

    private static final User NULL_USER = null;
    private static final Event EVENT = new Event();
    private static final String TEST_FIRSTNAME = "Sergei";

    @Test
    public void checkStrategiesCount() {
        assertThat(discountService.getStrategies().size(), is(2));
    }

    @Test
    public void testThatNotRegisteredUserCanGetDiscountForTenthTicket() {
        byte discount = discountService.getDiscount(NULL_USER, EVENT, LocalDateTime.now(), 11);
        assertThat(discount, is((byte) 50));
    }

    @Test
    public void testThatNotRegisteredUserCantGetDiscountForLessThan10Tickets() {
        byte discount = discountService.getDiscount(NULL_USER, EVENT, LocalDateTime.now(), 8);
        assertThat(discount, is((byte) 0));
    }
}