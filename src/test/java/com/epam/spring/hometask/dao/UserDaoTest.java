package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
public class UserDaoTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_FIRSTNAME = "Sergei";
    private static final String TEST_LASTNAME = "Albrant";
    private static final String TEST_EMAIL = "albrant@gmail.com";
    private static final Long SECOND_TEST_ID = 3L;
    private static final String SECOND_TEST_FIRSTNAME = "Petya";
    private static final String SECOND_TEST_LASTNAME = "Petin";
    private static final String SECOND_TEST_EMAIL = "petin@gmail.com";
    private static final LocalDate TEST_BIRTHDAY = LocalDate.of(1995, Month.MARCH, 23);
    private static final LocalDate SECOND_TEST_BIRTHDAY = LocalDate.of(1971, Month.APRIL, 2);

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao.save(buildUser(null, TEST_FIRSTNAME, TEST_LASTNAME, TEST_BIRTHDAY, TEST_EMAIL));
        userDao.save(buildUser(null, "Ivan", "Ivanov", LocalDate.of(1994,Month.APRIL, 3), "ivanov@gmail.com"));
    }

    private User buildUser(Long id, String firstName, String lastName, LocalDate birthday, String email) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(birthday);
        return user;
    }

    @Test
    public void getUserByEmail() {
        User userByEmail = userDao.getUserByEmail(TEST_EMAIL);
        assertNotNull(userByEmail);
        assertNotNull(userByEmail.getId());
        assertNotNull(userByEmail.getFirstName());
        assertNotNull(userByEmail.getLastName());
        assertNotNull(userByEmail.getBirthday());
        assertNotNull(userByEmail.getEmail());

        assertThat(userByEmail.getId(), is(TEST_ID));
        assertThat(userByEmail.getFirstName(), is(TEST_FIRSTNAME));
        assertThat(userByEmail.getLastName(), is(TEST_LASTNAME));
        assertThat(userByEmail.getBirthday(), is(TEST_BIRTHDAY));
        assertThat(userByEmail.getEmail(), is(TEST_EMAIL));
    }

    @Test
    public void save() {
        User user = buildUser(null, SECOND_TEST_FIRSTNAME, SECOND_TEST_LASTNAME, SECOND_TEST_BIRTHDAY,
                SECOND_TEST_EMAIL);

        assertNotNull(user);

        userDao.save(user);

        assertThat(user.getId(), is(SECOND_TEST_ID));
        assertThat(user.getFirstName(), is(SECOND_TEST_FIRSTNAME));
        assertThat(user.getLastName(), is(SECOND_TEST_LASTNAME));
        assertThat(user.getBirthday(), is(SECOND_TEST_BIRTHDAY));
        assertThat(user.getEmail(), is(SECOND_TEST_EMAIL));

    }

    @Test
    public void remove() {
        User user = userDao.getById(1L);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getBirthday());
        assertNotNull(user.getEmail());

        userDao.remove(user);

        User userAfter = userDao.getById(1L);

        assertNull(userAfter);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void getById() {
        User userById = userDao.getById(TEST_ID);
        assertNotNull(userById);
        assertNotNull(userById.getId());
        assertNotNull(userById.getFirstName());
        assertNotNull(userById.getLastName());
        assertNotNull(userById.getBirthday());
        assertNotNull(userById.getEmail());

        assertThat(userById.getId(), is(TEST_ID));
        assertThat(userById.getFirstName(), is(TEST_FIRSTNAME));
        assertThat(userById.getLastName(), is(TEST_LASTNAME));
        assertThat(userById.getBirthday(), is(TEST_BIRTHDAY));
        assertThat(userById.getEmail(), is(TEST_EMAIL));
    }

    @Test
    public void getAll() {
        List<User> users = userDao.getAll();

        assertNotNull(users);
        assertThat(users, hasSize(2));

        users.forEach(user -> {
            assertNotNull(user);
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
            assertNotNull(user.getBirthday());
            assertNotNull(user.getEmail());
        });
    }
}