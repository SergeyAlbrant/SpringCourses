package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.domain.User;
import com.epam.spring.hometask.service.discount.BirthdayDiscountStrategy;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.TenthTicketDiscountStrategy;
import lombok.Getter;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Getter
public class DiscountAspect {

    Map<User, Long> ticketDiscount = new HashMap<>();
    Map<User, Long> birthdayDiscount = new HashMap<>();
    Map<Class<? extends DiscountStrategy>, Long> totalCountMap = new HashMap<>();

    @AfterReturning(pointcut = "execution(* com.epam.spring.hometask.service.discount.BirthdayDiscountStrategy" +
            ".getDiscount(..)) && args(user,..)", returning = "discount")
    public void countOfBirthdayDiscount(User user, byte discount) {
        if (user != null && discount > 0) {
            if (!birthdayDiscount.containsKey(user)) {
                birthdayDiscount.put(user, 0L);
            }
            birthdayDiscount.put(user, birthdayDiscount.get(user) + 1);
        }

        totalCountUpdate(BirthdayDiscountStrategy.class);

    }

    @AfterReturning(pointcut = "execution(* com.epam.spring.hometask.service.discount.TenthTicketDiscountStrategy" +
            ".getDiscount(..)) && args(user,..)", returning = "discount")
    public void countOfTicketDiscount(User user, byte discount) {
        if (user != null && discount > 0) {

            if (!ticketDiscount.containsKey(user)) {
                ticketDiscount.put(user, 0L);
            }
            ticketDiscount.put(user, ticketDiscount.get(user) + 1);
        }

        totalCountUpdate(TenthTicketDiscountStrategy.class);
    }

    private void totalCountUpdate(Class<? extends DiscountStrategy> clazz) {
        if (!totalCountMap.containsKey(clazz)) {
            totalCountMap.put(clazz, 0L);
        }
        totalCountMap.put(clazz, totalCountMap.get(clazz) + 1);
    }
}
