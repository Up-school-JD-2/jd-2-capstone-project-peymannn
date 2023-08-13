package io.upschool.service;

import io.upschool.dto.request.AirportSaveRequest;
import io.upschool.dto.response.AirportSaveResponse;
import io.upschool.dto.response.FlightSaveResponse;
import io.upschool.entity.Airport;
import io.upschool.exception.BusinessException;
import io.upschool.repository.AirportRepository;
import io.upschool.service.Interface.AirportService;
import io.upschool.utils.AirlineSystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<AirportSaveResponse> getAll() {
        var allAirport = airportRepository.findAll();
        List<AirportSaveResponse> list = allAirport.stream().map(airport -> getAirportSaveResponse(airport)).toList();
        return list;
    }

    @Transactional
    @Override
    public AirportSaveResponse save(AirportSaveRequest request) {
        Airport airport = getAirport(request);
        Airport savedAirport = airportRepository.save(airport);
        return getAirportSaveResponse(savedAirport);
    }

    @Override
    public List<AirportSaveResponse> searchAirportsByLocation(String location) {
        var filteredAirports = airportRepository.findAllByAddress(location);
        List<AirportSaveResponse> list = filteredAirports.stream().map(airport -> getAirportSaveResponse(airport)).toList();
        return list;
    }

    @Override
    public Airport getReferenceById(Long id) {
        return airportRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(Long id) {
        var airport = airportRepository.findById(id).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        airport.setIsActive(false);
        airportRepository.save(airport);
    }

    private static Airport getAirport(AirportSaveRequest request) {
        return Airport.builder()
                .airportName(request.getAirportName())
                .address(request.getAddress())
                .build();
    }

    private static AirportSaveResponse getAirportSaveResponse(Airport savedAirport) {
        return AirportSaveResponse.builder()
                .airportName(savedAirport.getAirportName())
                .address(savedAirport.getAddress())
                .build();
    }
}
