package com.springbootapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String filedName;
    private long fieldValue;



    public ResourceNotFoundException(String resourceName, String filedName, long fieldValue) {
        super(String.format("%s not found %s: %s",resourceName,filedName,fieldValue));
        this.resourceName = resourceName;
        this.filedName = filedName;
        this.fieldValue = fieldValue;
    }

    public long getFieldValue() {
        return fieldValue;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
