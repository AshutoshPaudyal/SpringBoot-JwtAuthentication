package com.example.newoffceproject.exception;

import lombok.Data;

@Data
public class ResourceNotFound extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public ResourceNotFound(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s: %d does not exist",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFound(String resourceName, String fieldName) {
        super(String.format("%s with %s does not exist",resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }


}
