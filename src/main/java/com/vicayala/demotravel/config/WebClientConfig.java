package com.vicayala.demotravel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.base.url}")
    private String baseUrl;
    @Value("${api.api-key}")
    private String apiKey;
    @Value("${api.api-key.header}")
    private String apiKeyHeader;

    @Bean(name = "currency")
    public WebClient currencyWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apiKey)
                .build();
    }

    @Bean(name = "base")
    @Primary
    public WebClient baseWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apiKey)
                .build();
    }
}
