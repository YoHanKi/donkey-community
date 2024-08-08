package com.community.security.filter;

import com.community.security.exception.AccessTokenException;
import com.community.util.JWTUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * TokenCheckFilter
 * 로그인한 사용자인지 체크하는 로그인 체크용 필터와 유사하게 JWT 토큰을 검사하는 역할을 위해 사용
 *
 * spring의 OncePerRequestFilter를 상속, 하나의 요청에 대해 한번씩 동작하는 필터이다.
 */
@Slf4j
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public TokenCheckFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(path.startsWith("/document/manage") || path.startsWith("/member/modifyInfo") || path.startsWith("/member/withdrawal")){
            log.info("Token check filter.....................");
            log.info("JWTUtil : " + jwtUtil);

            try {
                //토큰 검사 이후 다음 필터로 전송
                validateAccessToken(request);
                filterChain.doFilter(request,response);
            } catch (AccessTokenException accessTokenException) {
                accessTokenException.sendResponseError(response);
            }
        }
        else{
            filterChain.doFilter(request,response);
            return ;
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
//        String headerStr = request.getHeader("Authorization");
        JsonParser jsonParser = new JsonParser();
        JsonObject tokens = jsonParser.parse(request.getHeader("Authorization")).getAsJsonObject();

        String tokenStr = tokens.get("accessToken").getAsString();
        log.info(tokenStr);


        if (tokenStr == null || tokenStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }
        // Bearer 검사
//        String tokenType = headerStr.substring(0, 6);
//        String tokenStr = headerStr.substring(7);

//        if (!"Bearer".equalsIgnoreCase(tokenType)) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
//        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            log.error("SignatureException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("ExpiredJwtException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
