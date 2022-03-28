package com.example.springbootexam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistEmailException extends RuntimeException{
    public AlreadyExistEmailException(String message) {
        super(message);
    }
}
