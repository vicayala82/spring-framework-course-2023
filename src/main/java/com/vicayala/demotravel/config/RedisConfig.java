package com.vicayala.demotravel.config;


import com.vicayala.demotravel.util.ServiceConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class RedisConfig {

    @Value("${cache.redis.address}")
    private String serverAddress;
    @Value("${cache.redis.password}")
    private String serverPassword;

    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();
        config.useSingleServer()
                .setAddress(serverAddress)
                .setPassword(serverPassword);
        RedissonClient redissonClient = Redisson.create(config);
        log.info("redisson client created");
        return redissonClient;
    }

    @Bean
    @Autowired
    public CacheManager cacheManager(RedissonClient redissonClient) {
        var configs = Map.of(
                ServiceConstants.FLY_CACHE_NAME, new CacheConfig(),
                ServiceConstants.HOTEL_CACHE_NAME, new CacheConfig()
        );
        return new RedissonSpringCacheManager(redissonClient, configs);
    }

    @CacheEvict(cacheNames ={
            ServiceConstants.FLY_CACHE_NAME,
            ServiceConstants.HOTEL_CACHE_NAME
    }, allEntries = true)
    @Scheduled(cron = ServiceConstants.SCHEDULED_RESET_CACHE)
    @Async
    public void deleteCache() {
        log.info("Clean cache");
    }


}
