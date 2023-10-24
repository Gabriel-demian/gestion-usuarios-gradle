package com.bci.gestionusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String resourceName, String fieldValue) {
        super(String.format("%s password is not a correct: '%s'", resourceName, fieldValue));
    }
}
