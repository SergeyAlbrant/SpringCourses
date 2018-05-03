package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.domain.User;

public interface UserDao extends AbstractDao<User> {

    User getUserByEmail(String email);
}
