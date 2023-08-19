package com.example.newoffceproject.exception;

import lombok.Data;

@Data
public class SameResourceFound extends RuntimeException{

    private String message;
    public SameResourceFound(String message) {
        super(String.format("%s",message));
        this.message = message;
    }
}
