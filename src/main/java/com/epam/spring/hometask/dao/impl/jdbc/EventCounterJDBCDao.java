package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.domain.Event;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class EventCounterJDBCDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long getCounterByName(Event event) {
        return jdbcTemplate.queryForObject("SELECT count FROM event_counter WHERE event_id=? AND " +
                "event_counter_case='BY_NAME'", new Object[]{event.getId()}, Long.class);
    }

    public Long getCounterByPrice(Event event) {
        return jdbcTemplate.queryForObject("SELECT count FROM event_counter WHERE event_id=? AND " +
                "event_counter_case='BY_PRICE'", new Object[]{event.getId()}, Long.class);
    }

    public Long getCounterByBooking(Event event) {
        return jdbcTemplate.queryForObject("SELECT count FROM event_counter WHERE event_id=? AND " +
                "event_counter_case='BY_BOOKING'", new Object[]{event.getId()}, Long.class);
    }

    public void putCounterByName(Event event, Long count) {
        jdbcTemplate.update("UPDATE event_counter SET count = ? WHERE event_id=? AND " +
                "event_counter_case='BY_NAME'", count, event.getId());
    }

    public void putCounterByPrice(Event event, Long count) {
        jdbcTemplate.update("UPDATE event_counter SET count = ? WHERE event_id=? AND " +
                "event_counter_case='BY_PRICE'", count, event.getId());
    }

    public void putCounterByBooking(Event event, Long count) {
        jdbcTemplate.update("UPDATE event_counter SET count = ? WHERE event_id=? AND " +
                "event_counter_case='BY_BOOKING'", count, event.getId());
    }


}
