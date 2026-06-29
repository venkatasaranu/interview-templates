package com.aa.space.explorer.exceptionhandling;

import com.aa.space.explorer.domain.Apod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApodRequestException.class)
    public ResponseEntity<Mono<Apod>> handleApodRequestException(ApodRequestException ex){
        Apod apod = new Apod();
        apod.setStatus(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(apod));
    }
}
