package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.configuration.MainConfig;
import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.discount.BirthdayDiscountStrategy;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.TenthTicketDiscountStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainConfig.class})
public class DiscountAspectTest {

    @Autowired
    DiscountService discountService;
    @Autowired
    DiscountAspect discountAspect;

    @Autowired
    @Qualifier("mapUserDao")
    UserDao userDao;

    private User user;
    private User secondUser;
    private static final Event EVENT = new Event();
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void initEvent() {
        user = new User();
        user.setFirstName("First");
        user.setBirthday(NOW.toLocalDate());
        EVENT.addAirDateTime(NOW.plusDays(1));
        userDao.save(user);

        secondUser = new User();
        secondUser.setFirstName("Second");
        secondUser.setBirthday(NOW.toLocalDate().minusDays(1));
        userDao.save(secondUser);
    }

    @Test
    public void countOfBirthdayDiscount() {
        discountService.getStrategies().get(0).getDiscount(user, EVENT, NOW, 2);
        discountService.getStrategies().get(0).getDiscount(user, EVENT, NOW, 2);

        Map<User, Long> birthdayDiscount = discountAspect.getBirthdayDiscount();
        Map<Class<? extends DiscountStrategy>, Long> totalCountMap = discountAspect.getTotalCountMap();

        assertNotNull(birthdayDiscount);
        assertThat(birthdayDiscount.size(), is(1));
        assertThat(birthdayDiscount.get(user), is(2L));

        assertThat(totalCountMap.size(), is(1));
        assertThat(totalCountMap.get(BirthdayDiscountStrategy.class), is(2L));
    }

    @Test
    public void countOfTicketDiscount() {
        DiscountStrategy discountStrategy = discountService.getStrategies().get(1);
        discountStrategy.getDiscount(user, EVENT, NOW, 11);
        discountStrategy.getDiscount(secondUser, EVENT, NOW, 12);

        Map<User, Long> ticketDiscount = discountAspect.getTicketDiscount();
        Map<Class<? extends DiscountStrategy>, Long> totalCountMap = discountAspect.getTotalCountMap();

        assertNotNull(ticketDiscount);
        assertThat(ticketDiscount.size(), is(2));
        assertThat(ticketDiscount.get(user), is(1L));
        assertThat(ticketDiscount.get(secondUser), is(1L));

        assertThat(totalCountMap.size(), is(1));
        assertThat(totalCountMap.get(TenthTicketDiscountStrategy.class), is(2L));
    }
}