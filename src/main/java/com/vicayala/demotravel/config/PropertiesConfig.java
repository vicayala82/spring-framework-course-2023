package com.vicayala.demotravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:configs/api_currency.properties"),
        @PropertySource("classpath:configs/redis.properties")
})
public class PropertiesConfig {
}
