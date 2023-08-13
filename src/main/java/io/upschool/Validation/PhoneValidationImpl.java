package io.upschool.Validation;

import io.upschool.utils.AirlineSystemConstant;

public class PhoneValidationImpl {
    public static boolean isValid(String phone) {
        if (!(phone.length() == AirlineSystemConstant.PHONE_NUMBER_LENGTH && phone.startsWith("05"))) {
            return false;
        }
        return true;
    }
}
