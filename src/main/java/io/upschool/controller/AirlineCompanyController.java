package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.AirlineCompanySaveRequest;
import io.upschool.dto.request.FlightSaveRequest;
import io.upschool.dto.response.AirlineCompanySaveResponse;
import io.upschool.dto.response.FlightSaveResponse;
import io.upschool.service.AirlineCompanyServiceImpl;
import io.upschool.service.FlightServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static io.upschool.controller.FlightController.getFlightSaveResponseList;

@RestController
@RequestMapping("/api/v1/AirlineCompany")
@RequiredArgsConstructor
public class AirlineCompanyController {
    private final AirlineCompanyServiceImpl airlineCompanyServiceImpl;
    private final FlightServiceImpl flightServiceImpl;

    @GetMapping("/airlineCompanies")
    public ResponseEntity<Object> getAirlineCompanies() {
        var airlineCompanySaveResponseList = airlineCompanyServiceImpl.getAll();
        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Object> createAirport(@Valid @RequestBody AirlineCompanySaveRequest request) {
        var airlineCompanySaveResponse = airlineCompanyServiceImpl.save(request);
        List<AirlineCompanySaveResponse> airlineCompanySaveResponseList = getAirlineCompanySaveResponseList(airlineCompanySaveResponse);

        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).successMessage("The new airline company has been identified.").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/flight")
    public ResponseEntity<Object> createFlight(@Valid @RequestBody FlightSaveRequest request) {
        var flightSaveResponse = flightServiceImpl.save(request);
        List<FlightSaveResponse> flightSaveResponseList = getFlightSaveResponseList(flightSaveResponse);

        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(flightSaveResponseList).successMessage("The new flight has been identified.").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchAirlineCompaniesByName")
    public ResponseEntity<Object> findAirlineCompaniesByName(@RequestParam("name") String query) {
        var airlineCompanySaveResponseList = airlineCompanyServiceImpl.searchAirlineCompaniesByName(query);
        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchFlight/{id}")
    public ResponseEntity<Object> findAirlineCompaniesByFlightRoute(@RequestParam("to") String to, @RequestParam("from") String from, @PathVariable("id") Long id) {
        var flightSaveResponseList = flightServiceImpl.searchFlightsByTwoAddressAndAirlineCompany(to, from, id);
        var response = BaseResponse.<FlightSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(flightSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public void deleteAirlineCompany(@PathVariable("id") Long id) {
        airlineCompanyServiceImpl.deleteById(id);
    }

    private static List<AirlineCompanySaveResponse> getAirlineCompanySaveResponseList(AirlineCompanySaveResponse airlineCompanySaveResponse) {
        List<AirlineCompanySaveResponse> airlineCompanySaveResponseList = new ArrayList<>();
        airlineCompanySaveResponseList.add(airlineCompanySaveResponse);
        return airlineCompanySaveResponseList;
    }
}

