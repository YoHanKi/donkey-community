package com.community.controller;

import com.community.domain.dto.ErrorResult;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] illegalExHandle", e);
        return new ErrorResult("잘못된 입력", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResult runtimeExHandle(RuntimeException e) {
        log.error("[exceptionHandle] RuntimeExHandle", e);
        return new ErrorResult("잘못된 사용자", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResult NotFoundExHandle(EntityNotFoundException e) {
        log.error("[exceptionHandle] EntityNotFoundExHandle", e);
        return new ErrorResult("존재하지 않는 데이터", e.getMessage());
    }

    //JwtException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtException.class)
    public ErrorResult JWTExHandle(JwtException e) {
        log.error("[exceptionHandle] JWTExHandle", e);
        return new ErrorResult("인증 오류", "인증 되지 않은 회원 입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResult AccessDeniedExHandle(AccessDeniedException e) {
        log.error("[exceptionHandle] AccessDeniedExHandle", e);
        return new ErrorResult("권한 오류", "접근 권한이 없습니다.");
    }
}
