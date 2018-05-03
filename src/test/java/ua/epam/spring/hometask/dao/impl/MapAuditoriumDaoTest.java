package ua.epam.spring.hometask.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.User;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class MapAuditoriumDaoTest {

    @Autowired
    private MapAuditoriumDao auditoriumDao;

    @Before
    public void setUp() {
    }

    @Test
    public void getByName() {
    }

    @Test
    public void getAll() {
        Set<Auditorium> auditoriums = auditoriumDao.getAll();

        assertNotNull(auditoriums);
        assertThat(auditoriums, hasSize(2));

        auditoriums.forEach(auditorium -> {
            assertNotNull(auditorium);
            assertNotNull(auditorium.getName());
            assertNotNull(auditorium.getNumberOfSeats());
            assertNotNull(auditorium.getVipSeats());
        });
    }
}