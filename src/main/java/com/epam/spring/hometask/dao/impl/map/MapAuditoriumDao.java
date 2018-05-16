package com.epam.spring.hometask.dao.impl.map;

import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Repository
public class MapAuditoriumDao implements AuditoriumDao {

    @Resource(name="auditoriumsMap")
    Map<Long, Auditorium> map = new HashMap<>();

    @Override
    public Auditorium getByName(String name) {
        if (!ValidationUtils.isNotEmpty(name)) {
            throw new IllegalArgumentException("Empty name " + name);
        }
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
