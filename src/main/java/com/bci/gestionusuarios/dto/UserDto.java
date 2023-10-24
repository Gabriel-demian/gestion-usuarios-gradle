package com.bci.gestionusuarios.dto;

import com.bci.gestionusuarios.entity.Phone;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

}
