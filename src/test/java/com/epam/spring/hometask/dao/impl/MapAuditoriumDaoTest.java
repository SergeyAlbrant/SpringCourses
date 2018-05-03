package com.epam.spring.hometask.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class MapAuditoriumDaoTest {

    private static final String TEST_NAME = "Usual";
    @Autowired
    private MapAuditoriumDao auditoriumDao;

    @Before
    public void setUp() {
    }

    @Test
    public void getByName() {
        Auditorium auditoriumByName = auditoriumDao.getByName(TEST_NAME);
        assertNotNull(auditoriumByName);
        assertNotNull(auditoriumByName.getName());
        assertNotNull(auditoriumByName.getVipSeats());
        assertNotNull(auditoriumByName.getAllSeats());

        assertThat(auditoriumByName.getName(), is(TEST_NAME));
        assertThat(auditoriumByName.getNumberOfSeats(), not(lessThan(0L)));
        assertThat(auditoriumByName.getVipSeats().size(), is(3));
        assertThat(auditoriumByName.getAllSeats().size(), is(20));
    }

    @Test
    public void getAll() {
        Set<Auditorium> auditoriums = auditoriumDao.getAll();

        assertNotNull(auditoriums);
        assertThat(auditoriums, hasSize(2));

        auditoriums.forEach(auditorium -> {
            assertNotNull(auditorium);
            assertNotNull(auditorium.getName());
            assertThat(auditorium.getNumberOfSeats(), not(lessThan(0L)));
            assertNotNull(auditorium.getVipSeats());
        });
    }
}