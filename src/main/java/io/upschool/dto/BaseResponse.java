package io.upschool.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private int status;

    @JsonProperty("is_Success")
    private boolean isSuccess;

    @Builder.Default
    private String error = "no message available.";

    private String successMessage;

    private List<T> dataList;

}
