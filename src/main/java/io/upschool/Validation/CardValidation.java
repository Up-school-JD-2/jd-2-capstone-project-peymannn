package io.upschool.Validation;

import io.upschool.utils.AirlineSystemConstant;

public class CardValidation {
    public static boolean IsNotValid(String cardNumber) {
        if (cardNumber.length() != AirlineSystemConstant.CARD_NUMBER_LENGTH) {
            return true;
        }
        return false;
    }
}
