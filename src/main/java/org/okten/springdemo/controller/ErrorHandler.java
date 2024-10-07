package org.okten.springdemo.controller;

import org.okten.springdemo.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleIllegalArgumentError(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(ErrorDTO.builder()
                        .message(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String details = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField().concat(" ").concat(error.getDefaultMessage()))
                .collect(Collectors.joining(".\n"));

        return ResponseEntity
                .badRequest()
                .body(ErrorDTO.builder()
                        .message(details)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
