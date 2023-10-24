package com.bci.gestionusuarios.util;

public class Validation {
    public static boolean isEmailValid(String email) {
        // Debe seguir el formato aaaaaaa@undominio.algo
        String emailRegex = Constants.EMAIL_REGEX;
        return email.matches(emailRegex);
    }

    public static boolean isPasswordValid(String password) {
        // Debe tener una mayúscula y dos números, longitud entre 8 y 12
        String passwordRegex = Constants.PASSWORD_REGEX;
        return password.matches(passwordRegex);
    }


}
