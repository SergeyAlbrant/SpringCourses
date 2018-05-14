package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.domain.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
@ComponentScan(basePackages = "com.epam")
@EnableAspectJAutoProxy
public class MainConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Map<Long, Auditorium> auditoriumsMap() {
        Map<Long, Auditorium> map = new HashMap<>();
        map.put(1L, firstAuditorium());
        map.put(2L, secondAuditorium());
        return map;
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
