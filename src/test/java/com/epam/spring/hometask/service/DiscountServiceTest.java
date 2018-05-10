package com.epam.spring.hometask.service;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class DiscountServiceTest {


    @Autowired
    private DiscountService discountService;

    private static final User NULL_USER = null;
    private static final Event EVENT = new Event();
    private static final User NOT_NULL_USER = new User();
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void init() {
        NOT_NULL_USER.setBirthday(NOW.toLocalDate());
        EVENT.addAirDateTime(NOW.plusDays(3));
    }

    @Test
    public void checkStrategiesCount() {
        assertThat(discountService.getStrategies().size(), is(2));
    }

    @Test
    public void testThatNotRegisteredUserCanGetDiscountForTenthTicket() {
        byte discount = discountService.getDiscount(NULL_USER, EVENT, NOW, 11);
        assertThat(discount, is((byte) 50));
    }

    @Test
    public void testThatNotRegisteredUserCantGetDiscountForLessThan10Tickets() {
        byte discount = discountService.getDiscount(NULL_USER, EVENT, NOW, 8);
        assertThat(discount, is((byte) 0));
    }

    @Test
    public void testThatUserCanGetBirthdayDiscount() {
        byte discount = discountService.getDiscount(NOT_NULL_USER, EVENT, NOW.plusDays(3), 8);
        assertThat(discount, is((byte) 5));
    }

    @Test
    public void testThatUserCantGetBirthdayDiscountForMoreThan5DaysDifference() {
        byte discount = discountService.getDiscount(NOT_NULL_USER, EVENT, NOW.plusDays(6), 8);
        assertThat(discount, is((byte) 0));
    }

    @Test
    public void testThatUserWithBirthdayGetsDiscountForTenthTicketBecauseItIsBigger() {
        byte discount = discountService.getDiscount(NOT_NULL_USER, EVENT, NOW.plusDays(3), 10);
        assertThat(discount, is((byte) 50));
    }
}