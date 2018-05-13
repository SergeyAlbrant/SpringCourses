package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.Ticket;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class CounterAspect {

    Map<Event, Long> counterByName = new HashMap<>();
    Map<Event, Long> counterByPrice = new HashMap<>();
    Map<Event, Long> counterByBooking = new HashMap<>();

    @AfterReturning(pointcut = "execution(* com.epam.spring.hometask.service.EventService.getByName(..))",
            returning = "event")
    public void countOfGetByNameMethod(Event event) {
        if (!counterByName.containsKey(event)) {
            counterByName.put(event, 0L);
        }
        counterByName.put(event, counterByName.get(event) + 1);
    }

    @AfterReturning(pointcut = "execution(* com.epam.spring.hometask.domain.Event.getBasePrice(..)) " +
            "&& target(event)")
    public void countOfGetPriceMethod(Event event) {
        if (!counterByPrice.containsKey(event)) {
            counterByPrice.put(event, 0L);
        }
        counterByPrice.put(event, counterByPrice.get(event) + 1);
    }

    @AfterReturning("execution(* com.epam.spring.hometask.service.BookingService.bookTickets(..)) " +
            "&& args(tickets)")
    public void countOfBookingMethod(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {

            Event event = ticket.getEvent();
            if (!counterByBooking.containsKey(event)) {
                counterByBooking.put(event, 0L);
            }
            counterByBooking.put(event, counterByBooking.get(event) + 1);
        }

    }


}
