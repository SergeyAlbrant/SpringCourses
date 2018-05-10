package com.epam.spring.hometask.configuration;

import com.epam.spring.hometask.service.DiscountService;
import com.epam.spring.hometask.service.discount.BirthdayDiscountStrategy;
import com.epam.spring.hometask.service.discount.DiscountStrategy;
import com.epam.spring.hometask.service.discount.TenthTicketDiscountStrategy;
import com.epam.spring.hometask.service.impl.DiscountServiceImpl;
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
    public DiscountService discountService() {
        DiscountService discountService = new DiscountServiceImpl();
        List<DiscountStrategy> discountStrategies = Arrays.asList(birthdayStrategy(), tenthDayStrategy());
        discountService.setStrategies(discountStrategies);
        return discountService;
    }
}
