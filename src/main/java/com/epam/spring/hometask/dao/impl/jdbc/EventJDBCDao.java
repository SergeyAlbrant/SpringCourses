package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventRating;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Getter
public class EventJDBCDao implements EventDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Event save(Event event) {
        jdbcTemplate.update("INSERT INTO events(name, date_time, base_price, rating) VALUES (?,?,?)",
                getPreparedStatementSetter(event));
        return event;
    }

    @Override
    public void remove(Event event) {
        jdbcTemplate.update("DELETE FROM events WHERE id=?", event.getId());
    }

    @Override
    public Event getById(Long entityId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM events WHERE id=?", rowMapper, entityId);
        }
        catch (EmptyResultDataAccessException ex) {
            return  null;
        }
    }

    @Override
    public List<Event> getAll() {
        return jdbcTemplate.query("SELECT * FROM events", rowMapper);
    }

    @Override
    public Event getByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM events WHERE name=?", rowMapper, name);
        }
        catch (EmptyResultDataAccessException ex) {
            return  null;
        }
    }

    @Override
    public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM events WHERE date_time BETWEEN ? AND ?", rowMapper,
                Timestamp.valueOf(from.atStartOfDay()), Timestamp.valueOf(to.atStartOfDay())));
    }

    @Override
    public Set<Event> getNextEvents(LocalDateTime to) {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM events WHERE date_time > ?", rowMapper,
                Timestamp.valueOf(to)));
    }

    private PreparedStatementSetter getPreparedStatementSetter(final Event event) {
        return ps -> {
            int i = 0;
            ps.setString(++i, event.getName());
            ps.setTimestamp(++i, Timestamp.valueOf(event.getAirDates().pollFirst()));
            ps.setDouble(++i, event.getBasePrice());
            ps.setString(++i, event.getRating().toString());
        };
    }

    private RowMapper<Event> rowMapper = (rs, rowNum) -> {
        Event event = new Event();
        event.setId(rs.getLong("id"));
        event.setName(rs.getString("name"));
        event.addAirDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        event.setBasePrice(rs.getLong("base_price"));
        event.setRating(EventRating.valueOf(rs.getString("rating")));
        return event;
    };


}
