package com.example.newoffceproject.exception;

import lombok.Data;

@Data
public class InvalidPhoneNumberCountryCodeException extends RuntimeException{
    private String message;
    public InvalidPhoneNumberCountryCodeException(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
