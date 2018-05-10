package com.epam.spring.hometask.dao.impl;

import com.epam.spring.hometask.utils.ValidationUtils;
import com.epam.spring.hometask.dao.AbstractDao;
import com.epam.spring.hometask.domain.DomainObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MapAbstractDao<T extends DomainObject> implements AbstractDao<T> {

    private final AtomicLong id = new AtomicLong(1L);

    Map<Long, T> map = new HashMap<>();

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(id.getAndIncrement());
        }

        map.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public void remove(T entity) {
        if (map.containsKey(entity.getId())) {
            map.remove(entity.getId());
        } else {
            throw new IllegalArgumentException("Entity with id "
                    + entity.getId() + " does not exists");
        }
    }

    @Override
    public T getById(Long entityId) {
        if (!ValidationUtils.isValidId(entityId))
            throw new IllegalArgumentException("Entity with id " + entityId + " does not exists");
        return map.get(entityId);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(map.values());
    }
}
