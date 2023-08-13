package io.upschool.Validation;

public class EmailValidationImpl {
    public static boolean isValid(String email) {
        //allow to only gmail address
        if (email.endsWith("gmail.com")) {
            return true;
        }
        return false;
    }
}
