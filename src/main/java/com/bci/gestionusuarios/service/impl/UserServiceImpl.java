package com.bci.gestionusuarios.service.impl;

import com.bci.gestionusuarios.entity.UserEntity;
import com.bci.gestionusuarios.exception.InvalidPasswordException;
import com.bci.gestionusuarios.exception.UserAlreadyExistException;
import com.bci.gestionusuarios.exception.UserNotFoundException;
import com.bci.gestionusuarios.repository.UserRepository;
import com.bci.gestionusuarios.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService, AuthenticationManager {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastLogin(LocalDateTime.now());
        return saveUser(user);
    }

    private UserEntity saveUser(UserEntity userEntity){
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new UserAlreadyExistException("User","email", userEntity.getEmail());
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User", "id", id.toString()));

        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById((UUID.fromString(username)))
                .orElseThrow(() -> new UserNotFoundException("User", "id", username));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserEntity user = userRepository.findById((UUID) authentication.getPrincipal())
                .orElseThrow(() -> new UserNotFoundException("User", "id", authentication.getPrincipal().toString()));
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            throw new InvalidPasswordException("Password", authentication.getCredentials().toString());
        }else{
            return authentication;
        }
    }
}
