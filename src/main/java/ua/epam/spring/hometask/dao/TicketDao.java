package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.time.LocalDateTime;
import java.util.Set;

public interface TicketDao extends AbstractDao<Ticket> {

    public Set<Ticket> getAllByEventAndDate(Event event, LocalDateTime dateTime);

}
