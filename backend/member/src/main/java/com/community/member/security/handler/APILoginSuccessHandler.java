package com.community.member.security.handler;

import com.community.member.global.util.JWTUtil;
import com.community.member.global.util.RedisUtil;
import com.community.member.member.domain.entity.Member;
import com.community.member.member.repository.MemberRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> claim = Map.of("email", authentication.getName());
        //Access Token 유효기간 1시간
        String accessToken = jwtUtil.generateToken(claim, 1);
        //Refresh Token 유효기간 1일
        String refreshToken = jwtUtil.generateToken(claim, 24);

        Gson gson = new Gson();

        Map<String, String> keyMap = Map.of("accessToken", accessToken, "refreshToken", refreshToken);

        //레디스에 저장
        Member member = memberRepository.findByEmail(authentication.getName()).orElse(new Member());
        redisUtil.set(member.getEmail(), member.getMemberRole().getRoleName(), 60);

        String jsonStr = gson.toJson(keyMap);

        response.getWriter().println(jsonStr);
    }
}