package com.community.gateway.global.config;

import com.community.gateway.filter.TokenFilter;
import com.community.gateway.global.util.JWTUtil;
import com.community.gateway.global.util.RedisUtil;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    public GatewayConfig(JWTUtil jwtUtil, RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Bean
    public GlobalFilter tokenFilter() {
        return new TokenFilter(jwtUtil, redisUtil);
    }
}