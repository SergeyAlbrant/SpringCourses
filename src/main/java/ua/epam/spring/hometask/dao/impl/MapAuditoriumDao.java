package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapAuditoriumDao implements AuditoriumDao {

    Map<Long, Auditorium> map = new HashMap<>();

    @Override
    public Auditorium getByName(String name) {
        List<Auditorium> auditoriums = map.values()
                                     .stream()
                                     .filter(auditorium -> auditorium.getName().equals(name))
                                     .collect(Collectors.toList());
        if (auditoriums.size() > 1) {
            throw new IllegalStateException("More than one auditorium with such name " + name);
        }

        return auditoriums.get(0);
    }

    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(map.values());
    }

}