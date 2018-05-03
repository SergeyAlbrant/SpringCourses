package com.epam.spring.hometask.service.impl;

import lombok.Getter;
import lombok.Setter;
import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.discount.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiscountServiceImpl implements DiscountService {

    List<DiscountStrategy> strategies;

    @Override
    public byte getDiscount(@Nullable User user,
                            @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {

        byte discount = 0;

        for (DiscountStrategy strategy : strategies) {
            discount = (byte) Math.max(strategy.getDiscount(user, event, airDateTime, numberOfTickets), discount);
        }

        return discount;
    }
}
