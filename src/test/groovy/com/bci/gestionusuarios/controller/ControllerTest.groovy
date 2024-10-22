package com.bci.gestionusuarios.controller

import com.bci.gestionusuarios.dto.UserDto
import com.bci.gestionusuarios.dto.UserMapper
import com.bci.gestionusuarios.entity.UserEntity
import com.bci.gestionusuarios.exception.InvalidPasswordFormatException
import com.bci.gestionusuarios.service.UserService
import com.bci.gestionusuarios.service.impl.TokenServiceImpl
import spock.lang.Specification


class ControllerTest extends Specification {

    private UserController userController
    private UserService userService
    private TokenServiceImpl tokenService

    def setup() {
        userService = Mock()
        tokenService = Mock()
        userController = new UserController(userService, tokenService)
    }

    def "signUp should create a new user and return a ResponseDto with the user and token"() {
        given:
        UserEntity userEntity = new UserEntity()
        userEntity.setName("Name")
        userEntity.email = "test@example.com"
        userEntity.password = "a2asfGfdfdf4"

        def userDto = UserMapper.toDto(userEntity, "token")
        userService.createUser(_) >> userEntity
        tokenService.toToken(_)>> "token"
        when:
        def response = userController.signUp(userDto)

        then:
        noExceptionThrown()
        response != null
        response.token == "token"
    }

    def "signUp should throw InvalidPasswordFormatException if the user password is invalid"() {
        given:

        def userDto = UserDto.builder()
                .name("Name")
                .email("test@test.com")
                .password("password123")
                .build()

        when:
        userController.signUp(userDto)

        then:
        thrown(InvalidPasswordFormatException)
    }
}
