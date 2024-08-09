package com.community.gateway.filter;


import com.community.gateway.global.exception.AccessTokenException;
import com.community.gateway.global.util.JWTUtil;
import com.community.gateway.global.util.RedisUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public class TokenFilter implements GlobalFilter, Ordered {
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/")) {

            try {
                // 토큰 검사
                Map<String, Object> claim = validateAccessToken(exchange);

                //레디스에서 정보 가져옴
                String email = (String) claim.get("email");
                String role = (String) redisUtil.get(email);

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(builder -> builder.header("u-Email", email)
                                .header("u-Role", role))
                        .build();

                // 다음 필터로 전송
                return chain.filter(mutatedExchange);
            } catch (AccessTokenException accessTokenException) {
                // 에러 응답
                return accessTokenException.sendResponseError(exchange);
            }
        } else {
            return chain.filter(exchange);
        }
    }

    private Map<String, Object> validateAccessToken(ServerWebExchange exchange) throws AccessTokenException {
        JsonElement jsonElement = JsonParser.parseString(exchange.getRequest().getHeaders().getFirst("Authorization"));
        JsonObject tokens = jsonElement.getAsJsonObject();

        String tokenStr = tokens.get("accessToken").getAsString();


        if (tokenStr == null || tokenStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
