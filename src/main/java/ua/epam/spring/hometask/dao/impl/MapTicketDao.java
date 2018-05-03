package ua.epam.spring.hometask.dao.impl;


import lombok.Getter;
import lombok.Setter;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MapTicketDao extends MapAbstractDao<Ticket> implements TicketDao {

    @Override
    public Set<Ticket> getAllByEventAndDate(Event event, LocalDateTime dateTime) {
        return map.values()
                  .stream()
                  .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime))
                  .collect(Collectors.toSet());
    }
}
