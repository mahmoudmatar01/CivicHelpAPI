package org.civichelpapi.civichelpapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        RedisCacheConfiguration defaultConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration(
                        "ngoReports",
                        defaultConfig.entryTtl(Duration.ofSeconds(60))
                )
                .build();
    }


}
