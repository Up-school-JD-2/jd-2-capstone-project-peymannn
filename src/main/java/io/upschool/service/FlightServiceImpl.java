package io.upschool.service;

import io.upschool.dto.request.FlightSaveRequest;
import io.upschool.dto.response.FlightSaveResponse;
import io.upschool.dto.response.RouteSaveResponse;
import io.upschool.entity.AirlineCompany;
import io.upschool.entity.Airport;
import io.upschool.entity.Flight;
import io.upschool.entity.Route;
import io.upschool.repository.AirlineCompanyRepository;
import io.upschool.repository.AirportRepository;
import io.upschool.repository.FlightRepository;
import io.upschool.repository.RouteRepository;
import io.upschool.service.Interface.AirlineCompanyService;
import io.upschool.service.Interface.FlightService;
import io.upschool.service.Interface.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final RouteService routeService;
    private final AirlineCompanyService airlineCompanyService;
    private final FlightRepository flightRepository;

    @Override
    public List<FlightSaveResponse> searchFlightsByAirlineCompanyName(String name) {
        var allFlight = flightRepository.findAllByAirlineCompanyNameContainingIgnoreCase(name);
        List<FlightSaveResponse> list = allFlight.stream().map(flight -> getFlightSaveResponse(flight)).toList();
        return list;
    }

    @Override
    public List<FlightSaveResponse> searchFlightsByTwoAddress(String to, String from) {
        var allFlight = flightRepository.findAllByRouteDestinationPlaceAddressAndRouteDeparturePlaceAddress(to, from);
        List<FlightSaveResponse> list = allFlight.stream().map(flight -> getFlightSaveResponse(flight)).toList();
        return list;
    }

    @Override
    public Flight getReferenceById(Long id) {
        return flightRepository.getReferenceById(id);
    }

    @Override
    public List<FlightSaveResponse> getAll() {
        var allFlight = flightRepository.findAll();
        List<FlightSaveResponse> list = allFlight.stream().map(flight -> getFlightSaveResponse(flight)).toList();
        return list;
    }

    @Transactional
    @Override
    public FlightSaveResponse save(FlightSaveRequest request) {
        Route route = routeService.getReferenceById(request.getRoute_id());
        AirlineCompany airlineCompany = airlineCompanyService.getReferenceById(request.getAirline_company_id());

        Flight newFlight = getFlight(request, route, airlineCompany);
        Flight flight = flightRepository.save(newFlight);

        return getFlightSaveResponse(flight);
    }

    private static Flight getFlight(FlightSaveRequest request, Route route, AirlineCompany airlineCompany) {
        return Flight.builder().route(route).airlineCompany(airlineCompany).flightDate(request.getFlightDate()).price(request.getPrice()).build();
    }

    private static FlightSaveResponse getFlightSaveResponse(Flight flight) {
        return FlightSaveResponse.builder().destinationPlace(flight.getRoute().getDestinationPlace().getAirportName() + " - " + flight.getRoute().getDestinationPlace().getAddress()).departurePlace(flight.getRoute().getDeparturePlace().getAirportName() + " - " + flight.getRoute().getDeparturePlace().getAddress()).price(flight.getPrice()).flightDate(flight.getFlightDate()).AirlineCompanyName(flight.getAirlineCompany().getName()).build();
    }

}
