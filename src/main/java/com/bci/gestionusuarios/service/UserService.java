package com.bci.gestionusuarios.service;

import com.bci.gestionusuarios.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity getUserById(UUID id);
}
