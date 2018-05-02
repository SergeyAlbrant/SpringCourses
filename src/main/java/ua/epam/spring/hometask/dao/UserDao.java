package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

public interface UserDao extends AbstractDao<User> {

    User getUserByEmail(String email);
}
