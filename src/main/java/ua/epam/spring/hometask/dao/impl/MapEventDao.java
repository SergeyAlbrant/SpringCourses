package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

import java.util.List;
import java.util.stream.Collectors;

public class MapEventDao extends MapAbstractDao<Event> implements EventDao {

    @Override
    public Event getByName(String name) {
        List<Event> events = map.values()
                              .stream()
                              .filter(event -> event.getName().equals(name))
                              .collect(Collectors.toList());
        if (events.size()>1){
            throw new IllegalStateException("More than one event with such name " + name);
        }

        return events.get(0);
    }
}
