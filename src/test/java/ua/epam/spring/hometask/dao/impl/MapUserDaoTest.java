package ua.epam.spring.hometask.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MapUserDaoTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_FIRSTNAME = "Sergei";
    private static final String TEST_LASTNAME = "Albrant";
    private static final String TEST_EMAIL = "albrant@gmail.com";
    private static final Long SECOND_TEST_ID = 3L;
    private static final String SECOND_TEST_FIRSTNAME = "Petya";
    private static final String SECOND_TEST_LASTNAME = "Petin";
    private static final String SECOND_TEST_EMAIL = "petin@gmail.com";

    private MapUserDao userDao;

    @Before
    public void setUp() {
        userDao = new MapUserDao();

        userDao.save(buildUser(null, TEST_FIRSTNAME, TEST_LASTNAME, TEST_EMAIL));
        userDao.save(buildUser(null, "Ivan", "Ivanov", "ivanov@gmail.com"));
    }

    private User buildUser(Long id, String firstName, String lastName, String email) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    @Test
    public void getUserByEmail() {
        User userByEmail = userDao.getUserByEmail(TEST_EMAIL);
        assertNotNull(userByEmail);
        assertNotNull(userByEmail.getId());
        assertNotNull(userByEmail.getFirstName());
        assertNotNull(userByEmail.getLastName());
        assertNotNull(userByEmail.getEmail());

        assertThat(userByEmail.getId(), is(TEST_ID));
        assertThat(userByEmail.getFirstName(), is(TEST_FIRSTNAME));
        assertThat(userByEmail.getLastName(), is(TEST_LASTNAME));
        assertThat(userByEmail.getEmail(), is(TEST_EMAIL));
    }

    @Test
    public void save() {
        User user = buildUser(null, SECOND_TEST_FIRSTNAME, SECOND_TEST_LASTNAME, SECOND_TEST_EMAIL);

        assertNotNull(user);

        userDao.save(user);

        assertThat(user.getId(), is(SECOND_TEST_ID));
        assertThat(user.getFirstName(), is(SECOND_TEST_FIRSTNAME));
        assertThat(user.getLastName(), is(SECOND_TEST_LASTNAME));
        assertThat(user.getEmail(), is(SECOND_TEST_EMAIL));

    }

    @Test
    public void remove() {
        User user = userDao.getById(1L);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
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
        assertNotNull(userById.getEmail());

        assertThat(userById.getId(), is(TEST_ID));
        assertThat(userById.getFirstName(), is(TEST_FIRSTNAME));
        assertThat(userById.getLastName(), is(TEST_LASTNAME));
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
            assertNotNull(user.getEmail());
        });
    }
}