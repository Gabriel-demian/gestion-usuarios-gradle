package com.bci.gestionusuarios.service;

import com.bci.gestionusuarios.entity.UserEntity;
import com.bci.gestionusuarios.exception.UserAlreadyExistException;
import com.bci.gestionusuarios.exception.UserNotFoundException;
import com.bci.gestionusuarios.repository.UserRepository;
import com.bci.gestionusuarios.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void createUser_shouldCreateNewUser() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setName("Ejemplo Usuario");
        user.setEmail("test@example.com");
        user.setPassword("Aa123456789");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("Aa123456789");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        // Act
        UserEntity createdUser = userService.createUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("Aa123456789", createdUser.getPassword());
    }

    @Test
    public void createUser_shouldThrowUserAlreadyExistException_whenEmailAlreadyExists() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setName("Ejemplo Usuario");
        user.setEmail("test@example.com");
        user.setPassword("Aa123456789");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("Aa123456789");

        userService.createUser(user);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        UserEntity user2 = new UserEntity();
        user2.setEmail("test@example.com");
        user2.setPassword("password");

        // Act
        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user2));
    }

    @Test
    public void getUserById_shouldReturnExistingUser() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setPassword("password");


        assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
    }

    @Test
    public void getUserById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

}
