package com.bci.gestionusuarios.controller

import com.bci.gestionusuarios.dto.UserDto
import com.bci.gestionusuarios.dto.UserMapper
import com.bci.gestionusuarios.entity.UserEntity
import com.bci.gestionusuarios.exception.InvalidPasswordFormatException
import com.bci.gestionusuarios.service.UserService
import com.bci.gestionusuarios.service.impl.TokenServiceImpl
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import java.security.Principal

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
        tokenService.toToken(_) >> "token"

        when:
        ResponseEntity response = userController.signUp(userDto)

        then:
        noExceptionThrown()
        response != null
        response.statusCodeValue == 201
        response.body.token == "token"
    }

    def "signUp should throw InvalidPasswordFormatException if the user password is invalid"() {
        given:
        def userDto = UserDto.builder()
                .name("Name")
                .email("test@test.com")
                .password("password123") // contraseña inválida
                .build()

        when:
        userController.signUp(userDto)

        then:
        thrown(InvalidPasswordFormatException)
    }

    def "login should return a UserDto with the user details and token"() {
        given:
        def userId = UUID.randomUUID()
        def principal = Mock(Principal) {
            getName() >> userId.toString()
        }

        UserEntity userEntity = new UserEntity()
        userEntity.setId(userId)
        userEntity.setName("Name")
        userEntity.email = "test@example.com"
        userEntity.password = "a2asfGfdfdf4"

        def userDto = UserMapper.toDto(userEntity, "token")
        userService.getUserById(userId) >> userEntity
        tokenService.toToken(_) >> "token"

        when:
        ResponseEntity response = userController.login(principal)

        then:
        noExceptionThrown()
        response != null
        response.statusCodeValue == 202
        response.body.token == "token"
        response.body.email == "test@example.com"
    }

    def "login should throw an exception when user is not found"() {
        given:
        def userId = UUID.randomUUID()
        def principal = Mock(Principal) {
            getName() >> userId.toString()
        }

        userService.getUserById(userId) >> { throw new RuntimeException("User not found") }

        when:
        userController.login(principal)

        then:
        def e = thrown(RuntimeException)
        e.message == "User not found"
    }
}
