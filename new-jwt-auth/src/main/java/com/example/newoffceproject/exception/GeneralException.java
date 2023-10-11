package com.example.newoffceproject.exception;

import lombok.Data;

@Data
public class GeneralException extends RuntimeException{
    private String message;
    public GeneralException(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
