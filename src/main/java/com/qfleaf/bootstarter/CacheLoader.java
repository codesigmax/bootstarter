package com.qfleaf.bootstarter;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CacheLoader implements CommandLineRunner {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        redisTemplate.opsForHash().put("globalConfig", "defaultUserRole", "1");
    }
}
