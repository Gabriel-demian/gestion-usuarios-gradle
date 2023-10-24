package com.bci.gestionusuarios.dto;

import com.bci.gestionusuarios.DataMock.UsersMock;
import com.bci.gestionusuarios.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    @Test
    @DisplayName("Test de creacion de usuarios")
    void userCreationTest() {

        UserEntity user = UsersMock.createUserEntity();

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("User1", user.getName()),
                () -> assertEquals("user@email.com", user.getEmail()),
                () -> assertEquals("Aa123456789", user.getPassword()),
                () -> assertEquals("+54", user.getPhones().get(0).getCountryCode()),
                () -> assertEquals(345790145L, user.getPhones().get(0).getNumber()),
                () -> assertEquals(261, user.getPhones().get(0).getCityCode())
        );

    }

}
