package com.bci.gestionusuarios.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResponseDto Tests")
public class ResponseDtoTest {

    @Test
    @DisplayName("Test de creacion de ResponseDto")
    public void testResponseDto() {
        ResponseDto responseDtoEmpty = ResponseDto.builder().build();

        UUID uuid = UUID.randomUUID();

        ResponseDto responseDtoFull = ResponseDto.builder()
                .id(uuid)
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .isActive(true)
                .build();

        assertNull(responseDtoEmpty.getId());
        assertNull(responseDtoEmpty.getCreated());
        assertNull(responseDtoEmpty.getLastLogin());
        assertNull(responseDtoEmpty.getToken());
        assertFalse(responseDtoEmpty.isActive());

        assertEquals(responseDtoFull.getId(), uuid);
        assertTrue(responseDtoFull.getCreated().isBefore(LocalDateTime.now()));
        assertTrue(responseDtoFull.getLastLogin().isBefore(LocalDateTime.now()));
        assertEquals(responseDtoFull.getToken(), "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        assertTrue(responseDtoFull.isActive());
    }
}
