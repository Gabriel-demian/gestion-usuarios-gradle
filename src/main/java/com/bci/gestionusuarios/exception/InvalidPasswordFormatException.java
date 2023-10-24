package com.bci.gestionusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s password is not a correct %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
