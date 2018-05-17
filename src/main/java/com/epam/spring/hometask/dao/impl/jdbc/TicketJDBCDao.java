package com.epam.spring.hometask.dao.impl.jdbc;

import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.dao.TicketDao;
import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Getter
public class TicketJDBCDao implements TicketDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("userJDBCDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("eventJDBCDao")
    private EventDao eventDao;


    @Override
    public Ticket save(Ticket ticket) {
        jdbcTemplate.update("INSERT INTO tickets(seat, date_time, user_id, event_id) VALUES (?,?,?,?)",
                getPreparedStatementSetter(ticket));
        return ticket;
    }

    @Override
    public void remove(Ticket ticket) {
        jdbcTemplate.update("DELETE FROM tickets WHERE id=?", ticket.getId());
    }

    @Override
    public Ticket getById(Long entityId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", rowMapper, entityId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Ticket> getAll() {
        return jdbcTemplate.query("SELECT * FROM tickets", rowMapper);
    }

    @Override
    public Set<Ticket> getAllByEventAndDate(Event event, LocalDateTime dateTime) {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM tickets WHERE event_id=? AND date_time=?", rowMapper,
                event.getId(), Date.valueOf(dateTime.));
    }

    private PreparedStatementSetter getPreparedStatementSetter(final Ticket ticket) {
        return ps -> {
            int i = 0;
            ps.setLong(++i, ticket.getSeat());
            ps.setDate(++i, Date.valueOf(ticket.getDateTime().toLocalDate()));
            ps.setLong(++i, ticket.getUser().getId());
            ps.setLong(++i, ticket.getEvent().getId());
        };
    }

    private RowMapper<Ticket> rowMapper = (rs, rowNum) -> {
        Ticket ticket = new Ticket(userDao.getById(rs.getLong("user_id")),
                eventDao.getById(rs.getLong("event_id")),
                rs.getTimestamp("date_time").toLocalDateTime(),
                rs.getLong("seat"));
        ticket.setId(rs.getLong("id"));
        return ticket;
    };
}
