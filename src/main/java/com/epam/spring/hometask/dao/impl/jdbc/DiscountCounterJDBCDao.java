package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class DiscountCounterJDBCDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long getCounterByUserAndStrategy(DiscountStrategy strategy, User user) {

        return jdbcTemplate.queryForObject("SELECT count FROM discount_counter WHERE user_id=? AND " +
                        "discount_strategy=?", new Object[]{user.getId(), strategy.getClass().getSimpleName()},
                Long.class);
    }

    public Long getCounterByStrategy(DiscountStrategy strategy) {

        return jdbcTemplate.queryForObject("SELECT SUM(count) FROM discount_counter WHERE discount_strategy=? " +
                        "GROUP BY discount_strategy", new Object[]{strategy.getClass().getSimpleName()}, Long.class);
    }


    public void updateCounter(DiscountStrategy strategy, User user, Long count) {
        jdbcTemplate.update("UPDATE discount_counter SET count = ? WHERE user_id=? AND " +
                "discount_strategy=?", count, user.getId(), strategy.getClass().getSimpleName());
    }

    public void createCounter(DiscountStrategy strategy, User user) {
        jdbcTemplate.update("INSERT INTO discount_counter(discount_strategy, count, user_id) VALUES " +
                "(?,?,?)", strategy.getClass().getSimpleName(), 0, user.getId());
    }




}
