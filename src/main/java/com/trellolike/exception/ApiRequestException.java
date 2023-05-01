package com.trellolike.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiRequestException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
