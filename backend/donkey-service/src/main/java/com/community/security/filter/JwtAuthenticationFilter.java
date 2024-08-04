package com.community.security.filter;

import com.community.domain.entity.Member;
import com.community.repository.MemberRepository;
import com.community.util.JWTUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Token 꺼내기
        String header = request.getHeader("Authorization");
        if (header != null) {
            try {
                JsonObject tokens = JsonParser.parseString(header).getAsJsonObject();
                String accessToken = null;

                if (tokens.has("accessToken")) {
                    accessToken = tokens.get("accessToken").getAsString();
                    //String refreshToken = tokens.get("refreshToken").getAsString();
                }

                if (accessToken != null) {
                    String userEmail = jwtUtil.getUserName(accessToken);
                    log.info(userEmail);
                    Member member = memberRepository.findByEmail(userEmail).orElse(null);
                    if (member != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), null, member.getAuthorities());
                        //디테일 설정하기
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (JsonSyntaxException e) {
                log.info("Error parsing JSON from Authorization header", e);
            } catch (JwtException e) {
                log.info("JwtAuthenticationFilter ERROR ", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
