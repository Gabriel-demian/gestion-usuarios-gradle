package com.bci.gestionusuarios.service

import com.bci.gestionusuarios.entity.UserEntity
import com.bci.gestionusuarios.exception.InvalidPasswordException
import com.bci.gestionusuarios.exception.UserAlreadyExistException
import com.bci.gestionusuarios.repository.UserRepository
import com.bci.gestionusuarios.service.impl.UserServiceImpl
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.*


class ServiceTest extends Specification{

    UserServiceImpl userService
    UserRepository userRepository
    PasswordEncoder passwordEncoder


    def setup() {
        userRepository = Stub(UserRepository)
        passwordEncoder = Stub(PasswordEncoder)
        userService = new UserServiceImpl(userRepository, passwordEncoder)

    }

    def "createUser should create a new user"() {
        given:
        UserEntity user = new UserEntity()
        user.setName("Name")
        user.email = "test@example.com"
        user.password = "password"

        userRepository.save(_)>> user

        when:
        def result = userService.createUser(user)

        then:
        noExceptionThrown()
        result != null
    }

    def "Test getUserById method"() {
        given:
        UserEntity user = new UserEntity(id: UUID.randomUUID(), email: "test@example.com", password: "password")
        userRepository.findById(_) >> Optional.of(user)
        userRepository.save(_)>> user

        when:
        def result = userService.getUserById(user.id)

        then:
        result == user
    }

    def "Test createUser method - duplicate user"() {
        given:
        UserEntity user = new UserEntity(email: "test@example.com", password: "password")

        userRepository.existsByEmail(_) >> { throw new UserAlreadyExistException("User", "email", user.email) }

        when:
        userService.createUser(user)

        then:
        thrown(UserAlreadyExistException)
    }

    def "Test loadUserByUsername method"() {
        given:
        UUID userId = UUID.randomUUID()
        UserEntity user = new UserEntity(id: userId, email: "test@example.com", password: "password")
        userRepository.findById(userId) >> Optional.of(user)

        when:
        UserDetails userDetails = userService.loadUserByUsername(userId.toString())

        then:
        userDetails.username == userId.toString()
        userDetails.password == user.password
        userDetails.authorities.isEmpty()
    }

    def "Test authenticate method - valid password"() {
        given:
        UUID userId = UUID.randomUUID()
        String password = "password"
        UserEntity user = new UserEntity(id: userId, email: "test@example.com", password: password)

        userRepository.findById(_) >> Optional.of(user)

        passwordEncoder.matches(_, _) >> true
        Authentication authentication = new Authentication() {
            @Override
            Collection<? extends GrantedAuthority> getAuthorities() {
                return null
            }

            @Override
            Object getCredentials() {
                return "something"
            }

            @Override
            Object getDetails() {
                return null
            }

            @Override
            Object getPrincipal() {
                return UUID.randomUUID()
            }

            @Override
            boolean isAuthenticated() {
                return false
            }

            @Override
            void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            String getName() {
                return null
            }
        }

        when:
        Authentication result = userService.authenticate(authentication)

        then:
        result == authentication
    }

    def "Test authenticate method - invalid password"() {
        given:
        UUID userId = UUID.randomUUID()
        String password = "password"
        UserEntity user = new UserEntity(id: userId, email: "test@example.com", password: password)
        userRepository.findById(_) >> Optional.of(user)

        passwordEncoder.matches(_, _) >> false

        Authentication authentication = new Authentication() {
            @Override
            Collection<? extends GrantedAuthority> getAuthorities() {
                return null
            }

            @Override
            Object getCredentials() {
                return "something"
            }

            @Override
            Object getDetails() {
                return null
            }

            @Override
            Object getPrincipal() {
                return UUID.randomUUID()
            }

            @Override
            boolean isAuthenticated() {
                return false
            }

            @Override
            void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            String getName() {
                return null
            }
        }

        when:
        userService.authenticate(authentication)

        then:
        thrown(InvalidPasswordException)
    }
}
