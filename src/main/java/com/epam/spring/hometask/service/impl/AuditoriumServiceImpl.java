package com.epam.spring.hometask.service.impl;


import com.epam.spring.hometask.domain.Auditorium;
import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@Setter
@Getter
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    @Autowired
    private AuditoriumDao auditoriumDao;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDao.getByName(name);
    }
}
