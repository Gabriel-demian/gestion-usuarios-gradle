package com.bci.gestionusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private int errorCode;
    private String errorMessage;

}
