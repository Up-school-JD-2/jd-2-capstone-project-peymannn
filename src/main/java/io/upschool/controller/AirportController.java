package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.AirportSaveRequest;
import io.upschool.dto.response.AirportSaveResponse;
import io.upschool.service.AirportServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airport")
@RequiredArgsConstructor
public class AirportController {
    private final AirportServiceImpl airportServiceImpl;

    @GetMapping("/airports")
    public ResponseEntity<Object> getAirports() {
        var airportSaveResponse = airportServiceImpl.getAll();
        var response = BaseResponse.<AirportSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airportSaveResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Object> createAirport(@Valid @RequestBody AirportSaveRequest request) {
        var airportSaveResponse = airportServiceImpl.save(request);
        List<AirportSaveResponse> airportSaveResponseList = getAirportSaveResponseList(airportSaveResponse);

        var response = BaseResponse.<AirportSaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(airportSaveResponseList).successMessage("The new airport has been identified.").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByLocation")
    public ResponseEntity<Object> findAirportsByLocationQuery(@RequestParam("location") String query) {
        var airportSaveResponseList = airportServiceImpl.searchAirportsByLocation(query);
        var response = BaseResponse.<AirportSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airportSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public void deleteAirport(@PathVariable("id") Long id) {
        airportServiceImpl.deleteById(id);
    }

    private static List<AirportSaveResponse> getAirportSaveResponseList(AirportSaveResponse airportSaveResponse) {
        List<AirportSaveResponse> airportSaveResponseList = new ArrayList<>();
        airportSaveResponseList.add(airportSaveResponse);
        return airportSaveResponseList;
    }
}
