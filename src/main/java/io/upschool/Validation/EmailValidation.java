package io.upschool.Validation;

public class EmailValidation {
    public static boolean IsNotValid(String email) {
        if (!email.contains("@") || !email.endsWith(".com")) {
            return true;
        }
        return false;
    }

}
