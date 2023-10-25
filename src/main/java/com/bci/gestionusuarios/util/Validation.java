package com.bci.gestionusuarios.util;

public class Validation {

    private Validation() {
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = Constants.EMAIL_REGEX;
        return email.matches(emailRegex);
    }

    public static boolean isPasswordValid(String password) {
        String passwordRegex = Constants.PASSWORD_REGEX;
        return password.matches(passwordRegex);
    }


}
