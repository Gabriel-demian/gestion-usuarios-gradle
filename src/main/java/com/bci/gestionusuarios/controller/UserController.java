package com.bci.gestionusuarios.controller;

import com.bci.gestionusuarios.dto.ResponseDto;
import com.bci.gestionusuarios.dto.UserDto;
import com.bci.gestionusuarios.dto.UserMapper;
import com.bci.gestionusuarios.entity.UserEntity;
import com.bci.gestionusuarios.exception.InvalidEmailFormatException;
import com.bci.gestionusuarios.exception.InvalidPasswordFormatException;
import com.bci.gestionusuarios.service.UserService;
import com.bci.gestionusuarios.service.impl.TokenServiceImpl;
import com.bci.gestionusuarios.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private TokenServiceImpl tokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody UserDto user) {
        if(!Validation.isEmailValid(user.getEmail())){
            throw new InvalidEmailFormatException("User", "email", user.getEmail());
        }

        if(!Validation.isPasswordValid(user.getPassword())){
            throw new InvalidPasswordFormatException("User", "password", user.getPassword());
        }

        UserEntity newUser = userService.createUser(UserMapper.toEntity(user));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toResponse(newUser, tokenService.toToken(newUser)));
    }

    @GetMapping("/login")
    public ResponseEntity<UserDto> login(Principal principal) {
        UserEntity user = userService.getUserById(UUID.fromString(principal.getName()));

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(UserMapper.toDto(user, tokenService.toToken(user)));
    }

}
