package com.trellolike.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    private Map<String, Object> error = new LinkedHashMap<>();

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Map<String, Object>> handleApiRequestException(ApiRequestException e) {
        error.put("detail", e.getMessage());
        error.put("status", e.getHttpStatus());
        error.put("status Code", e.getHttpStatus().value());

        return new ResponseEntity<>(error, e.getHttpStatus());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        error.put("detail", e.getCause().getCause().getMessage());
        error.put("status", HttpStatus.UNPROCESSABLE_ENTITY);
        error.put("status Code", HttpStatus.UNPROCESSABLE_ENTITY.value());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        error.put("detail", e.getCause().getMessage());
        error.put("status", HttpStatus.BAD_REQUEST);
        error.put("status Code", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        error.put("detail", e.getCause().getMessage());
        error.put("status", HttpStatus.BAD_REQUEST);
        error.put("status Code", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
