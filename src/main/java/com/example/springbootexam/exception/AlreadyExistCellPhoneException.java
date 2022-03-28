package com.example.springbootexam.exception;

public class AlreadyExistCellPhoneException extends RuntimeException {
    public AlreadyExistCellPhoneException(String message) {
        super(message);
    }
}
