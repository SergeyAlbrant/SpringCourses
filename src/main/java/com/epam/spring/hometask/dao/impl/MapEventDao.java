package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.dao.EventDao;
import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MapEventDao extends MapAbstractDao<Event> implements EventDao {

    @Override
    public Event getByName(String name) {
        List<Event> events = map.values()
                                .stream()
                                .filter(event -> event.getName().equals(name))
                                .collect(Collectors.toList());
        if (events.size() > 1) {
            throw new IllegalStateException("More than one event with such name " + name);
        }

        return events.get(0);
    }

    @Override
    public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
        return map.values()
                  .stream()
                  .filter(event -> event.airsOnDates(from, to))
                  .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(LocalDateTime to) {
        return map.values()
                  .stream()
                  .filter(event -> event.getAirDates()
                                        .stream()
                                        .anyMatch(dt -> dt.compareTo(LocalDateTime.now()) >= 0
                                                && dt.compareTo(to) <= 0))
                  .collect(Collectors.toSet());
    }
}