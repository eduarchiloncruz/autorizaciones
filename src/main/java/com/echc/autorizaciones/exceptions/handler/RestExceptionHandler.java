package com.echc.autorizaciones.exceptions.handler;

import com.echc.autorizaciones.commons.dtos.ErrorApiDTO;
import com.echc.autorizaciones.exceptions.customs.EntityModelNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityModelNotFoundException.class)
    public ResponseEntity<ErrorApiDTO> handleEntityNotFoundException(EntityModelNotFoundException ex, HttpServletRequest request) {
        ErrorApiDTO error = ErrorApiDTO.builder()
                .message(ex.getMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .code(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
