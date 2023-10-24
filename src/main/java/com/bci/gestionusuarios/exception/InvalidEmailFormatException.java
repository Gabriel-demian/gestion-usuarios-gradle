package com.bci.gestionusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s email is not a correct %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
