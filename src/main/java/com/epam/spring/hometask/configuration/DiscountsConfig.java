package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.service.discount.BirthdayDiscountStrategy;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.TenthTicketDiscountStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DiscountsConfig {

    @Bean
    public DiscountStrategy birthdayStrategy() {
        return new BirthdayDiscountStrategy();
    }

    @Bean
    public DiscountStrategy tenthDayStrategy() {
        return new TenthTicketDiscountStrategy();
    }

    @Bean
    public List<DiscountStrategy> strategies() {
        List<DiscountStrategy> discountStrategies = Arrays.asList(birthdayStrategy(), tenthDayStrategy());
        return discountStrategies;
    }
}
