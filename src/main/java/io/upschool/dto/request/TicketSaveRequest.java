package io.upschool.dto.request;

import io.upschool.utils.AirlineSystemConstant;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveRequest {

    @NotNull(message = "departurePlaceId may not be null")
    private Long flightId;

    @NotBlank(message = "passenger Name Surname may not be blank")
    private String passengerNameSurname;

    @NotBlank(message = "passenger Phone Number may not be blank")
    @Digits(fraction = 0, integer = 10, message = "enter a value between 0-10 the numbers.")
    private String passengerPhoneNumber;

    @Size(min = AirlineSystemConstant.CARD_NUMBER_LENGTH)
    @NotBlank(message = "Card Number may not be blank")
    private String CardNumber;
}