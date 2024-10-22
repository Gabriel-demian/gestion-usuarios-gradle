package com.bci.gestionusuarios.util


import spock.lang.Specification
import spock.lang.Title

@Title("Constants unit tests")
class ConstantsTest extends Specification {

    def "should match valid email addresses"() {
        expect:
        email.matches(Constants.EMAIL_REGEX)

        where:
        email << [
                "test@example.com",
                "user.name+tag+sorting@example.com",
                "user-name@example.co.uk",
                "user_name@example.io",
                "test.email@example.com"
        ]
    }

    def "should not match invalid email addresses"() {
        expect:
        !email.matches(Constants.EMAIL_REGEX)

        where:
        email << [
                "plainaddress",
                "@missingusername.com",
                "user@.com",
                "user@com",
                "user@domain..com",
                "user@domain,com"
        ]
    }

    def "should match valid passwords"() {
        expect:
        password.matches(Constants.PASSWORD_REGEX)

        where:
        password << [
                "Pass12word",    // Contiene al menos una mayúscula y dos dígitos
                "Abcvfd34",     // Contiene al menos una mayúscula y dos dígitos
                "Mypass99",     // Contiene al menos una mayúscula y dos dígitos
                "Tested12",     // Contiene al menos una mayúscula y dos dígitos
                "Validpass22"    // Contiene al menos una mayúscula y dos dígitos
        ]
    }

    def "should not match invalid passwords"() {
        expect:
        !password.matches(Constants.PASSWORD_REGEX)

        where:
        password << [
                "password",        // No mayúsculas
                "PASSWORD",        // No números
                "Pass123",        // Solo un dígito
                "12345678",        // No letras
                "Short1"          // No longitud mínima
        ]
    }
}