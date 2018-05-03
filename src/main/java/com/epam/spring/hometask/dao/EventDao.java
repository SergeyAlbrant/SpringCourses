package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface EventDao extends AbstractDao<Event> {

    Event getByName(String name);

    Set<Event> getForDateRange(LocalDate from, LocalDate to);

    Set<Event> getNextEvents(LocalDateTime to);
}
