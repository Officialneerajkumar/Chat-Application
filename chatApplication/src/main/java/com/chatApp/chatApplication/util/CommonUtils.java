package com.chatApp.chatApplication.util;

import java.util.regex.*;
public class CommonUtils {

    public static boolean isValidPassword(String password){
//        It contains at least 8 characters and at most 20 characters.
//        It contains at least one digit.
//        It contains at least one upper case alphabet.
//        It contains at least one lower case alphabet.
//        It contains at least one special character which includes !@#$%&*()-+=^.
//        It doesn’t contain any white space.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
//        The first digit should contain numbers between 6 to 9.
//        The rest 9 digit can contain any number between 0 to 9.
//        The mobile number can have 11 digits also by including 0 at the starting.
//        The mobile number can be of 12 digits also by including 91 at the starting
        // 1) Begins with 0 or 91
        // 2) Then contains 6,7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(phoneNumber);
        return (m.find() && m.group().equals(phoneNumber));
    }
}
