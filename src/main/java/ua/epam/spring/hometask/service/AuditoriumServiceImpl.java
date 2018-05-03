package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class AuditoriumServiceImpl implements AuditoriumService {

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
