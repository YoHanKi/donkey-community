package com.community.document.global.util;

import com.community.document.global.dto.RedisObject;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> documentRedisTemplate;

    public void setDocumentData(String key, Object value) {
        documentRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(value.getClass()));
        documentRedisTemplate.opsForValue().set(key, value);
    }

    public RedisObject getDocumentData(String key) {
        documentRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisObject.class));
        return (RedisObject) documentRedisTemplate.opsForValue().get(key);
    }

    public boolean deleteDocumentData(String key) {
        return Boolean.TRUE.equals(documentRedisTemplate.delete(key));
    }

    public boolean hasDocumentDataKey(String key) {
        return Boolean.TRUE.equals(documentRedisTemplate.hasKey(key));
    }
}