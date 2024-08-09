package com.community.member.global.util;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "documentRedisTemplate")
    private final RedisTemplate<String, Object> documentRedisTemplate;

    public void set(String key, Object o, int minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setDocumentData(String key, Object value) {
        documentRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(value.getClass()));
        documentRedisTemplate.opsForValue().set(key, value);
    }

    public Object getDocumentData(String key) {
        return documentRedisTemplate.opsForValue().get(key);
    }

    public boolean deleteDocumentData(String key) {
        return Boolean.TRUE.equals(documentRedisTemplate.delete(key));
    }

    public boolean hasDocumentDataKey(String key) {
        return Boolean.TRUE.equals(documentRedisTemplate.hasKey(key));
    }
}