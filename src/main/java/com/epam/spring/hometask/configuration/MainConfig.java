package com.epam.spring.hometask.configuration;


import com.epam.spring.hometask.dao.AuditoriumDao;
import com.epam.spring.hometask.dao.EventDao;
import com.epam.spring.hometask.dao.TicketDao;
import com.epam.spring.hometask.dao.UserDao;
import com.epam.spring.hometask.dao.impl.MapAuditoriumDao;
import com.epam.spring.hometask.dao.impl.MapEventDao;
import com.epam.spring.hometask.dao.impl.MapTicketDao;
import com.epam.spring.hometask.dao.impl.MapUserDao;
import com.epam.spring.hometask.domain.Auditorium;
import com.epam.spring.hometask.service.AuditoriumService;
import com.epam.spring.hometask.service.BookingService;
import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.EventService;
import com.epam.spring.hometask.service.UserService;
import com.epam.spring.hometask.service.impl.AuditoriumServiceImpl;
import com.epam.spring.hometask.service.impl.BookingServiceImpl;
import com.epam.spring.hometask.service.impl.EventServiceImpl;
import com.epam.spring.hometask.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@PropertySource("classpath:auditoriums.properties")
@Import(DiscountsConfig.class)
public class MainConfig {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public AuditoriumDao auditoriumMapDao() {
        MapAuditoriumDao mapAuditoriumDao = new MapAuditoriumDao();
        mapAuditoriumDao.setMap(auditoriumsMap());
        return mapAuditoriumDao;
    }

    @Bean
    public Map<Long, Auditorium> auditoriumsMap() {
        Map<Long, Auditorium> map = new HashMap<>();
        map.put(1L, firstAuditorium());
        map.put(2L, secondAuditorium());
        return map;
    }

    @Bean
    public EventDao eventMapDao() {
        return new MapEventDao();
    }

    @Bean
    public UserDao userMapDao() {
        return new MapUserDao();
    }

    @Bean
    public TicketDao ticketMapDao() {
        return new MapTicketDao();
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userMapDao());
        return userService;
    }

    @Bean
    public EventService eventService() {
        EventServiceImpl eventService = new EventServiceImpl();
        eventService.setEventDao(eventMapDao());
        return eventService;
    }

    @Bean
    public AuditoriumService auditoriumService() {
        AuditoriumServiceImpl auditoriumService = new AuditoriumServiceImpl();
        auditoriumService.setAuditoriumDao(auditoriumMapDao());
        return auditoriumService;
    }

    @Bean
    public BookingService bookingService() {
        BookingServiceImpl bookingService = new BookingServiceImpl();
        bookingService.setEventDao(eventMapDao());
        bookingService.setTicketDao(ticketMapDao());
        bookingService.setDiscountService(discountService);
        return bookingService;
    }

    @Bean
    public Auditorium firstAuditorium() {
        return createAuditorium("first");
    }

    @Bean
    public Auditorium secondAuditorium() {
        return createAuditorium("second");
    }

    private Auditorium createAuditorium(String prefix) {
        String nameString = env.getProperty(prefix + ".name");
        String strNumberOfSeats = env.getProperty(prefix + ".seats_number");

        Set<Long> vipSeats = new HashSet<>();
        String[] vipSeatsString = env.getProperty(prefix + ".vip_seats").split(",");

        for (String vipSeat : vipSeatsString) {
            vipSeats.add(Long.parseLong(vipSeat));
        }

        Auditorium auditorium = new Auditorium();
        auditorium.setName(nameString);
        auditorium.setNumberOfSeats(Long.parseLong(strNumberOfSeats));
        auditorium.setVipSeats(vipSeats);
        return auditorium;
    }
}
