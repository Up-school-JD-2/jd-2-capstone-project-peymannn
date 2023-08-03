package io.upschool.service.Interface;

import io.upschool.dto.request.AirportSaveRequest;
import io.upschool.dto.response.AirportSaveResponse;
import io.upschool.entity.Airport;

import java.util.List;

public interface AirportService extends GenericService<AirportSaveRequest, AirportSaveResponse> {
    List<AirportSaveResponse> searchAirportsByLocation(String location);

    Airport getReferenceById(Long id);

    void deleteById(Long id);
}
