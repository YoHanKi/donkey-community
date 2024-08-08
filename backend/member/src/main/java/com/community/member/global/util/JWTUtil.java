package com.community.member.global.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.secret}")
    private String key;


    // 인증 완료시 토큰 발급하는 메소드
    public String generateToken(Map<String,Object> valueMap, int hours){

        // JWT 헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");

        // JWT 페이로드 (내용) : 인증 정보 반환)
        Map<String, Object> payloads = new HashMap<>(valueMap);

        int time = (60) * hours;

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    // 토큰 검증 기능
    public Map<String, Object> validateToken(String token) throws JwtException{
        Map<String, Object> claim = null;

        claim = Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claim;
    }
    public String getEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }
}
