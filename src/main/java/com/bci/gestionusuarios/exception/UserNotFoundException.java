package com.bci.gestionusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is not present %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
