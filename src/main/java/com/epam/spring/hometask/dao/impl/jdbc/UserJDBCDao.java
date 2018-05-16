package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class UserJDBCDao implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User save(User entity) {
        jdbcTemplate.update("INSERT INTO BOOK (name, description) VALUES ('book name', 'book description')");
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
