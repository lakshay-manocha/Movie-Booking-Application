package com.Microservice.Catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest(classes = CatelogServiceApplication.class)
class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    void shouldConnectToRedis() {
        String key = "redis:test";
        String value = "Redis Connected";

        redisTemplate.opsForValue().set(key, value);

        String result = redisTemplate.opsForValue().get(key);

        assertNotNull(result);
        assertEquals(value, result);

        System.out.println("Redis connected successfully: " + result);
    }
}