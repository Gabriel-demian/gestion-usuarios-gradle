package com.bci.gestionusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is already present for the  %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
