package io.upschool.Validation;

public class RouteValidationImpl {
    public static boolean isValid(Long departurePlaceId, Long destinationPlaceId) {
        if (destinationPlaceId == departurePlaceId) {
            return false;
        }
        return true;
    }
}
