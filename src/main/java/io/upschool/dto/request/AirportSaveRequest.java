package io.upschool.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportSaveRequest {
    @NotBlank(message = "airport name may not be blank")
    private String airportName;

    @NotBlank(message = "address may not be blank")
    private String address;
}
