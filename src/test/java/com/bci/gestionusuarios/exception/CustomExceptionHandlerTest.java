package com.bci.gestionusuarios.exception;


import com.bci.gestionusuarios.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionHandlerTest {

    private CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    public void testHandleGlobalException() {
        Exception exception = new Exception("Error interno del servidor");

        ResponseEntity<ErrorResponseDto> responseEntity = customExceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertEquals(LocalDateTime.now(), errorResponseDto.getTimestamp());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponseDto.getErrorCode());
        assertEquals("Error interno del servidor", errorResponseDto.getErrorMessage());
    }

    @Test
    public void testHandleInvalidEmailFormatException() {
        InvalidEmailFormatException exception = new InvalidEmailFormatException("User","Email","some@email.com");

        ResponseEntity<ErrorResponseDto> responseEntity = customExceptionHandler.handleInvalidEmailFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponseDto.getErrorCode());
        assertEquals("User email is not a correct Email: 'some@email.com'", errorResponseDto.getErrorMessage());
    }

    @Test
    public void testHandleInvalidPasswordFormatException() {
        InvalidPasswordFormatException exception = new InvalidPasswordFormatException("User","Password","Asd1234");

        ResponseEntity<ErrorResponseDto> responseEntity = customExceptionHandler.handleInvalidPasswordFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponseDto.getErrorCode());
        assertEquals("User password is not a correct Password: 'Asd1234'", errorResponseDto.getErrorMessage());
    }

    @Test
    public void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User","El usuario no existe","Nombre");

        ResponseEntity<ErrorResponseDto> responseEntity = customExceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponseDto.getErrorCode());
        assertEquals("User is not present El usuario no existe: 'Nombre'", errorResponseDto.getErrorMessage());
    }
}
