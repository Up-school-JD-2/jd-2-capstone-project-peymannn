package io.upschool.service;

import io.upschool.Validation.RouteValidation;
import io.upschool.dto.request.RouteSaveRequest;
import io.upschool.dto.response.RouteSaveResponse;
import io.upschool.entity.Airport;
import io.upschool.entity.Route;
import io.upschool.exception.BusinessException;
import io.upschool.repository.RouteRepository;
import io.upschool.service.Interface.AirportService;
import io.upschool.service.Interface.RouteService;
import io.upschool.utils.AirlineSystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final AirportService airportService;

    @Override
    public List<RouteSaveResponse> getAll() {
        var allRoute = routeRepository.findAll();
        List<RouteSaveResponse> list = allRoute.stream().map(route -> getRouteSaveResponse(route)).toList();
        return list;
    }

    @Transactional
    @Override
    public RouteSaveResponse save(RouteSaveRequest request) {
        CheckValidations(request.getDeparturePlaceId(), request.getDestinationPlaceId());
        Airport departureAirport = airportService.getReferenceById(request.getDeparturePlaceId());
        Airport destinationAirport = airportService.getReferenceById(request.getDestinationPlaceId());

        Route route = getRoute(departureAirport, destinationAirport);
        Route savedRoute = routeRepository.save(route);
        return getRouteSaveResponse(savedRoute);
    }

    @Override
    public List<RouteSaveResponse> searchRoutesByLocation(String to, String from) {
        var filteredRoutes = routeRepository.findAllByDestinationPlaceAddressAndDeparturePlaceAddress(to, from);
        List<RouteSaveResponse> list = filteredRoutes.stream().map(route -> getRouteSaveResponse(route)).toList();
        return list;
    }

    @Override
    public Route getReferenceById(Long id) {
        return routeRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        var route = routeRepository.findById(id).get();
        route.setIsActive(false);
        routeRepository.save(route);
    }

    private static RouteSaveResponse getRouteSaveResponse(Route savedRoute) {
        return RouteSaveResponse.builder().destinationPlace(savedRoute.getDestinationPlace().getAirportName() + " - " + savedRoute.getDestinationPlace().getAddress()).departurePlace(savedRoute.getDeparturePlace().getAirportName() + " - " + savedRoute.getDeparturePlace().getAddress()).build();
    }

    private static Route getRoute(Airport departureAirport, Airport destinationAirport) {
        return Route.builder().departurePlace(departureAirport).destinationPlace(destinationAirport).build();
    }

    private static void CheckValidations(Long departurePlaceId, Long destinationPlaceId) {
        if (RouteValidation.IsNotValid(departurePlaceId, destinationPlaceId))
            throw new BusinessException(AirlineSystemConstant.INVALID_AIRLINE_ROUTE_EXCEPTION);

    }
}
