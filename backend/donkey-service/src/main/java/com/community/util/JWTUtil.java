package com.community.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JWTUtil {

    @Value("${org.zerock.jwt.secret}")
    private String key;


    //문자열 생성 기능
    public String generateToken(Map<String, Object> valueMap, int hours){


        log.info("generateToken..." + key);

        //헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        int time = (60) * hours;

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                //발급일 기준으로 시작일 지정
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                //발급일 기준 60 * 24 초 기준으로 만료일 생성
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwtStr;
    }

    //토큰 검증 기능
    public Map<String, Object> validateToken(String token) throws JwtException {

        Map<String, Object> claim = null;
        //11 버전
      claim = Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build()
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();

        // 9 버전
//        claim = Jwts.parser()
//                .setSigningKey(key.getBytes())  //키 지정
//                .parseClaimsJws(token)          //파싱 및 검증, 실패 시 에러
//                .getBody();

        return claim;
    }

    public String getUserName(String token) throws JwtException {
        Map<String, Object> claim = null;
        //11 버전
        claim = Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build()
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();

        return (String) claim.get("email");
    }

}