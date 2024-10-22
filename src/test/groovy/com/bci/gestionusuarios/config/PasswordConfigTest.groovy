package com.bci.gestionusuarios.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Title

@Title("PasswordConfig unit tests")
@SpringBootTest(classes = PasswordConfig)
class PasswordConfigTest extends Specification {

    @Autowired
    PasswordEncoder passwordEncoder

    def "should create a BCryptPasswordEncoder bean"() {
        expect:
        passwordEncoder instanceof BCryptPasswordEncoder
    }

    def "should correctly encode and match passwords"() {
        given: "A raw password"
        def rawPassword = "mySecretPassword"

        when: "Encoding the password"
        def encodedPassword = passwordEncoder.encode(rawPassword)

        then: "The encoded password should match the raw password"
        passwordEncoder.matches(rawPassword, encodedPassword)
    }
}