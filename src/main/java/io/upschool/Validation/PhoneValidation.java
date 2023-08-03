package io.upschool.Validation;

import io.upschool.utils.AirlineSystemConstant;

public class PhoneValidation {
    public static boolean IsNotValid(String phone) {
        if (phone.length() != AirlineSystemConstant.PHONE_NUMBER_LENGTH || !phone.startsWith("05")) {
            return true;
        }
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

}
