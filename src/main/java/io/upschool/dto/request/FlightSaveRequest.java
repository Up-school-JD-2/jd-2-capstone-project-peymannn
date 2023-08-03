package io.upschool.dto.request;

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
public class FlightSaveRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date flightDate;
    private double price;
    private Long airline_company_id;
    private Long route_id;
}
