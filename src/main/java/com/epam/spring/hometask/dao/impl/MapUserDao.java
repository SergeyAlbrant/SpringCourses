package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MapUserDao extends MapAbstractDao<User> implements UserDao {


    @Override
    public User getUserByEmail(String email) {

        if (!ValidationUtils.isNotEmpty(email)) {
            throw new IllegalArgumentException("Empty email " + email);
        }

        List<User> users = map.values()
                              .stream()
                              .filter(user -> user.getEmail().equals(email))
                              .collect(Collectors.toList());
        if (users.size() > 1) {
            throw new IllegalStateException("More than one user with such email " + email);
        }

        return users.get(0);
    }
}
