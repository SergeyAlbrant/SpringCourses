package com.epam.spring.hometask.aspect;

import com.epam.spring.hometask.domain.Event;
import com.epam.spring.hometask.domain.EventType;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CounterAspect {

    Map<Event, Map<EventType, Long>> counter = new HashMap<>();

    @AfterReturning(pointcut="execution(* com.epam.spring.hometask.service.EventService.getByName(..))",
            returning="event")
    public void countOfGetByNameMethod(Event event) throws Throwable {
        if (!counter.containsKey(event)) {

            Map<EventType, Long> eventMap = new HashMap<>();
            eventMap.put(EventType.NAME, 1L);

            counter.put(event, eventMap);
        }
        Map<EventType, Long> eventMap = counter.get(event);

        if (!eventMap.containsKey(EventType.NAME)) {
            eventMap.put(EventType.NAME, 1L);
        }

        counter.put(event, eventMap);
    }


}
