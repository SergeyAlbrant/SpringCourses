package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Repository
@Getter
public class UserJDBCDao implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", rowMapper, email);
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update("INSERT INTO users(first_name, last_name, email, birthday) VALUES (?,?,?,?)",
                getPreparedStatementSetter(user));
        return user;
    }

    @Override
    public void remove(User user) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
    }

    @Override
    public User getById(Long entityId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", rowMapper, entityId);
        }
        catch (EmptyResultDataAccessException ex) {
            return  null;
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", rowMapper);
    }

    private PreparedStatementSetter getPreparedStatementSetter(final User user) {
        return ps -> {
            int i = 0;
            ps.setString(++i, user.getFirstName());
            ps.setString(++i, user.getLastName());
            ps.setString(++i, user.getEmail());
            ps.setDate(++i, Date.valueOf(user.getBirthday()));
        };
    }

    private RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    };
}
