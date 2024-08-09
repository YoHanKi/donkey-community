package com.community.document.global.controller;


import com.community.document.global.dto.ErrorResult;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}