package com.acuity.Splick.util;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validation {
    public static boolean checkMail(String mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    public static boolean checkTextPattern(String txt) {
        Pattern pattern = null;
        final String PASSWORD_PATTERN = "^[a-zA-Z ]*$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(txt).matches();
    }

    public static boolean checkPassword(String password) {
        Pattern pattern = null;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        return (pattern.matcher(password).matches());
    }

}
