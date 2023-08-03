package io.upschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveRequest {
    private Long flightId;
    private String passengerNameSurname;
    private String passengerPhoneNumber;
    private String CardNumber;
}