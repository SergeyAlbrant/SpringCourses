package com.epam.spring.hometask.dao.impl.map;


import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.dao.TicketDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Repository
public class MapTicketDao extends MapAbstractDao<Ticket> implements TicketDao {

    @Override
    public Set<Ticket> getAllByEventAndDate(Event event, LocalDateTime dateTime) {
        return map.values()
                  .stream()
                  .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime))
                  .collect(Collectors.toSet());
    }
}
