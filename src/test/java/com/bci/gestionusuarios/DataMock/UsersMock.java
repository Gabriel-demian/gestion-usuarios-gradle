package com.bci.gestionusuarios.DataMock;

import com.bci.gestionusuarios.dto.UserDto;
import com.bci.gestionusuarios.entity.Phone;
import com.bci.gestionusuarios.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersMock {

    public static List<Phone> createPhoneList() {
        Phone phone1 = new Phone();
        phone1.setNumber(345790145L);
        phone1.setCityCode(261);
        phone1.setCountryCode("+54");

        Phone phone2 = new Phone();
        phone2.setNumber(155789456L);
        phone2.setCityCode(262);
        phone2.setCountryCode("+54");

        return Arrays.asList(phone1, phone2);
    }

    public static UserDto createUser() {

        return UserDto.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .isActive(true)
                .name("User1")
                .email("user@email.com")
                .password("Aa123456789")
                .phones(createPhoneList())
                .build();
    }

    public static Optional<UserDto> createOptionalUser() {

        return Optional.of(UserDto.builder()
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .isActive(true)
                .name("User1")
                .email("user@email.com")
                .password("Aa123456789")
                .phones(createPhoneList())
                .build());
    }

    public static UserEntity createUserEntity() {

        return UserEntity.builder()
                .id(UUID.randomUUID())
                .isActive(true)
                .name("User1")
                .email("user@email.com")
                .password("Aa123456789")
                .phones(createPhoneList())
                .build();
    }

    public static Optional<UserEntity> createOptionalUserEntity() {

        return  Optional.of(UserEntity.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .name("User1")
                .email("user@email.com")
                .password("Aa123456789")
                .phones(createPhoneList())
                .build());
    }


}
