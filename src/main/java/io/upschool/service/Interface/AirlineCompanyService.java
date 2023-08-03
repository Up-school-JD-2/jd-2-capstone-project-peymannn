package io.upschool.service.Interface;

import io.upschool.dto.request.AirlineCompanySaveRequest;
import io.upschool.dto.response.AirlineCompanySaveResponse;
import io.upschool.entity.AirlineCompany;

import java.util.List;

public interface AirlineCompanyService extends GenericService<AirlineCompanySaveRequest, AirlineCompanySaveResponse> {
    List<AirlineCompanySaveResponse> searchAirlineCompanysByName(String name);

    AirlineCompany getReferenceById(Long id);

    void deleteById(Long id);
}
