package io.upschool.service.Interface;

import io.upschool.dto.request.FlightSaveRequest;
import io.upschool.dto.response.FlightSaveResponse;
import io.upschool.entity.Flight;

import java.util.List;

public interface FlightService extends GenericService<FlightSaveRequest, FlightSaveResponse> {
    List<FlightSaveResponse> searchFlightsByAirlineCompanyName(String name);

    List<FlightSaveResponse> searchFlightsByTwoAddress(String to, String from);

    Flight getReferenceById(Long id);
}
