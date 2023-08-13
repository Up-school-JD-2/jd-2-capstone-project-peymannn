package io.upschool.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteSaveRequest {

    @NotNull(message = "departurePlaceId may not be null")
    private Long departurePlaceId;

    @NotNull(message = "destinationPlaceId may not be null")
    private Long destinationPlaceId;
}
