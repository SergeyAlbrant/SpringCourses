package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.User;

import java.util.List;

public class UserJDBCRepo implements UserDao {



    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public void remove(User entity) {

    }

    @Override
    public User getById(Long entityId) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
