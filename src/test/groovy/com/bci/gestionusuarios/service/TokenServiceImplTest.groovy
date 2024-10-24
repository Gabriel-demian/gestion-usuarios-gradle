package com.bci.gestionusuarios.service

import com.bci.gestionusuarios.entity.UserEntity
import com.bci.gestionusuarios.service.impl.TokenServiceImpl
import spock.lang.Specification
import spock.lang.Title

import java.time.LocalDateTime

@Title("TokenServiceImpl unit tests")
class TokenServiceImplTest extends Specification {

    TokenServiceImpl tokenService = new TokenServiceImpl()

    def "should generate a valid token for a given user"() {
        given: "A user entity with a unique ID"
        def user = UserEntity.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones([])
                .build()

        when: "Generating a token"
        def token = tokenService.toToken(user)

        then: "The token should be a non-null string"
        token != null
        token.length() > 0

        and: "The token should be able to extract claims"
        def claims = tokenService.getClaims(token)

        and: "The claims should contain the correct user ID"
        claims.get(TokenServiceImpl.userId) == user.getId().toString()
        claims.get("iss") == "gestion-usuarios"

        def expTimestamp = claims.get("exp")
        (expTimestamp instanceof Long || expTimestamp instanceof Integer)

        Date expirationDate = new Date(((Number) expTimestamp).longValue() * 1000)
        expirationDate > new Date()
    }

    def "should throw an exception for an invalid token"() {
        given: "An invalid token"
        def invalidToken = "invalid.jwt.token"

        when: "Trying to get claims from the invalid token"
        tokenService.getClaims(invalidToken)

        then: "An exception should be thrown"
        def exception = thrown(Exception)
        exception.message != null
    }
}