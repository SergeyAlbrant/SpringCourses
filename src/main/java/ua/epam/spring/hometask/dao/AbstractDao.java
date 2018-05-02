package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.DomainObject;

import java.util.List;

public interface AbstractDao<T extends DomainObject> {
    T save(T entity);

    void remove(T entity);

    T getById(Long entityId);

    List<T> getAll();

}
