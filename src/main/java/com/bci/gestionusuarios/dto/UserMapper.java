package com.bci.gestionusuarios.dto;

import com.bci.gestionusuarios.entity.Phone;
import com.bci.gestionusuarios.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(UserEntity user, String token){
        return UserDto.builder()
                .id(user.getId())
                .created(user.getCreated())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .lastLogin(LocalDateTime.now())
                .token(token)
                .isActive(user.isActive())
                .phones(toPhone(user.getPhones()))
                .build();
    }

    public static List<Phone> toPhone(List<Phone> phones) {
        if (phones == null || phones.isEmpty()) {
            return new ArrayList<>();
        }
        return phones.stream()
                .map(phone -> Phone.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build())
                .collect(Collectors.toList());
    }

    public static UserEntity toEntity(UserDto dto){
        return UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .created(LocalDateTime.now())
                .lastLogin(dto.getLastLogin())
                .isActive(true)
                .phones(toPhone(dto.getPhones()))
                .build();
    }

    public static ResponseDto toResponse(UserEntity userDto, String token){
        return ResponseDto.builder()
                .id(userDto.getId())
                .created(userDto.getCreated())
                .lastLogin(userDto.getLastLogin())
                .token(token)
                .isActive(userDto.isActive())
                .build();
    }

}
