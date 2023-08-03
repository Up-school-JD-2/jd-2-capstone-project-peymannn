package io.upschool.Validation;

import io.upschool.utils.AirlineSystemConstant;

public class RouteValidation {
    public static boolean IsNotValid(Long departurePlaceId,Long destinationPlaceId) {
        if (destinationPlaceId == departurePlaceId) {
            return true;
        }
        return false;
    }
}
