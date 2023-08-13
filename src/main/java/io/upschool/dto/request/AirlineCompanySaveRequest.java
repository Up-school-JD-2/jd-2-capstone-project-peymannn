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
public class AirlineCompanySaveRequest {
    @NotBlank(message = "name may not be blank")
    private String name;

    @NotBlank(message = "email may not be blank")
    private String email;
}
