package io.upschool.Validation;

import io.upschool.utils.AirlineSystemConstant;

public class CardValidationImpl {
    public static boolean isValid(String cardNumber) {
        if (cardNumber.length() != AirlineSystemConstant.CARD_NUMBER_LENGTH) {
            return false;
        }
        return true;
    }
}
