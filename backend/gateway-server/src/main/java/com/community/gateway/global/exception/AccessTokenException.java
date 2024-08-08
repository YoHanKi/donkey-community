package com.community.gateway.global.exception;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException {

    TOKEN_ERROR token_error;

    @Getter
    public enum TOKEN_ERROR {
        UNACCEPT(401, "Token is null or too short"),
        BADTYPE(401, "Token type Bearer"),
        MALFORM(403, "Malformed Token"),
        BADSIGN(403, "Badsignatured Token"),
        EXPIRED(403, "Expired Token");

        private int status;
        private String msg;

        TOKEN_ERROR(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

    }

    public AccessTokenException(TOKEN_ERROR error) {
        super(error.name());
        this.token_error = error;
    }

    public Mono<Void> sendResponseError(ServerWebExchange exchange) {
        exchange.getResponse().setRawStatusCode(token_error.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String responseStr = gson.toJson(Map.of("msg", token_error.getMsg(), "time", new Date()));

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(responseStr.getBytes(StandardCharsets.UTF_8));

        // writeWith가 비동기적으로 작업을 완료할 때까지 기다림
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
