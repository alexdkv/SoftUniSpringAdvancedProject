package com.finalProject.SoftUniProject.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String message) {
        super(message);

    }
}
