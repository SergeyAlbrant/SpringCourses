package com.epam.spring.hometask.service.discount;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BirthdayDiscountStrategy implements DiscountStrategy {
    @Override
    public byte getDiscount(@Nullable User user,
                            @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {

        LocalDate birthday = user.getBirthday();
        LocalDate airDate = airDateTime.toLocalDate();

        return Math.abs(ChronoUnit.DAYS.between(birthday, airDate)) > 5 ? (byte) 5 : (byte) 0;
    }
}
