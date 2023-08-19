package com.example.newoffceproject.exception;

public class ConfirmPasswordException extends RuntimeException{
    String message;
    public ConfirmPasswordException(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
