package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.AirlineCompanySaveRequest;
import io.upschool.dto.response.AirlineCompanySaveResponse;
import io.upschool.service.AirlineCompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/AirlineCompany")
@RequiredArgsConstructor
public class AirlineCompanyController {
    private final AirlineCompanyServiceImpl airlineCompanyServiceImpl;

    @GetMapping("/airlineCompanys")
    public ResponseEntity<Object> getAirlineCompanys() {
        var airlineCompanySaveResponseList = airlineCompanyServiceImpl.getAll();
        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/airlineCompany")
    public ResponseEntity<Object> createAirport(@RequestBody AirlineCompanySaveRequest request) {
        var airlineCompanySaveResponse = airlineCompanyServiceImpl.save(request);
        List<AirlineCompanySaveResponse> airlineCompanySaveResponseList = getAirlineCompanySaveResponseList(airlineCompanySaveResponse);

        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).successMessage("The new airline company has been identified.").build();
        return ResponseEntity.ok(response);
    }

    private static List<AirlineCompanySaveResponse> getAirlineCompanySaveResponseList(AirlineCompanySaveResponse airlineCompanySaveResponse) {
        List<AirlineCompanySaveResponse> airlineCompanySaveResponseList = new ArrayList<>();
        airlineCompanySaveResponseList.add(airlineCompanySaveResponse);
        return airlineCompanySaveResponseList;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAirlineCompanysByLocationQuery(@RequestParam("name") String query) {
        var airlineCompanySaveResponseList = airlineCompanyServiceImpl.searchAirlineCompanysByName(query);
        var response = BaseResponse.<AirlineCompanySaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(airlineCompanySaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public void deleteAirlineCompany(@PathVariable("id") Long id) {
        airlineCompanyServiceImpl.deleteById(id);
    }
}

