package com.epam.spring.hometask.service.impl;

import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Setter
@Getter
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User object) {
        return userDao.save(object);
    }

    @Override
    public void remove(@Nonnull User object) {
        userDao.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }
}
