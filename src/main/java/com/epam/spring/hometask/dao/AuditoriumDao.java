package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

public interface AuditoriumDao {

    Auditorium getByName(String name);

    Set<Auditorium> getAll();
}
