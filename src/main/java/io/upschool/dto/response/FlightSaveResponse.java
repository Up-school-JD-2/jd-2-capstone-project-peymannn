package io.upschool.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSaveResponse {
    private String departurePlace;
    private String destinationPlace;
    private double price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date flightDate;
    private String AirlineCompanyName;
}
