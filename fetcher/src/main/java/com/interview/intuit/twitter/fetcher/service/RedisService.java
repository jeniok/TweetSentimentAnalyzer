package com.interview.intuit.twitter.fetcher.service;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final StringRedisTemplate template;
    private ValueOperations<String, String> ops;

    public RedisService(StringRedisTemplate template) {
        this.template = template;
    }

    @PostConstruct
    private void init() {
        ops = template.opsForValue();
    }

    public void setKey(String key, String value) {
        ops.set(key, value);
    }

    public String getKey(String key) {
        return ops.get(key);
    }

    public String delKey(String key) {
        return ops.getAndDelete(key);
    }
}