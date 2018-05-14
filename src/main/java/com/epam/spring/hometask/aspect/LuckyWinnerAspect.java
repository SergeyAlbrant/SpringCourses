package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.domain.Ticket;
import lombok.Getter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@Getter
public class LuckyWinnerAspect {

    @Around("execution(* com.epam.spring.hometask.service.BookingService.bookTickets(..))")
    public void checkLucky(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!isLucky()) {
            joinPoint.proceed();
        } else {
            Set<Ticket> ticketSet = (Set<Ticket>) joinPoint.getArgs()[0];
            for (Ticket ticket : ticketSet) {
                ticket.getEvent().setBasePrice(0);
            }
            joinPoint.proceed();
        }
    }

    private boolean isLucky() {
        return Math.random()*100 > 50;
    }
}
