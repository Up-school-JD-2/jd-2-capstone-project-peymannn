package io.upschool.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSaveRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "flight date may not be null")
    private Date flightDate;

    private double price;

    @NotNull(message = "airline_company_id may not be null")
    private Long airline_company_id;

    @NotNull(message = "route_id may not be null")
    private Long route_id;
}
