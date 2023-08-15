package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.FlightSaveRequest;
import io.upschool.dto.response.FlightSaveResponse;
import io.upschool.service.FlightServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightServiceImpl flightServiceImpl;

    @GetMapping("/flights")
    public ResponseEntity<Object> getFlights() {
        var flightSaveResponse = flightServiceImpl.getAll();
        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(flightSaveResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Object> createFlight(@Valid @RequestBody FlightSaveRequest request) {
        var flightSaveResponse = flightServiceImpl.save(request);
        List<FlightSaveResponse> flightSaveResponseList = getFlightSaveResponseList(flightSaveResponse);

        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(flightSaveResponseList).successMessage("The new flight has been identified.").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchWithAirlineCompanyName/{airlineCompanyName}")
    public ResponseEntity<Object> findFlightsByAirlineCompanyName(@PathVariable("airlineCompanyName") String query) {
        var flightSaveResponseList = flightServiceImpl.searchFlightsByAirlineCompanyName(query);
        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(flightSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchFlightsByTwoAddress")
    public ResponseEntity<Object> findFlightsByTwoAddress(@RequestParam("to") String to, @RequestParam("from") String from) {
        var flightSaveResponseList = flightServiceImpl.searchFlightsByTwoAddress(to, from);
        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(flightSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    static List<FlightSaveResponse> getFlightSaveResponseList(FlightSaveResponse flightSaveResponse) {
        List<FlightSaveResponse> flightSaveResponseList = new ArrayList<>();
        flightSaveResponseList.add(flightSaveResponse);
        return flightSaveResponseList;
    }
}
