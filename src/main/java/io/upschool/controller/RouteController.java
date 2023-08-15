package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.RouteSaveRequest;
import io.upschool.dto.response.RouteSaveResponse;
import io.upschool.service.RouteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteServiceImpl routeServiceImpl;

    @GetMapping("/routes")
    public ResponseEntity<Object> getRoutes() {
        var routeSaveResponse = routeServiceImpl.getAll();
        var response = BaseResponse.<RouteSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(routeSaveResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Object> createRoute(@Valid @RequestBody RouteSaveRequest request) {
        var routeSaveResponse = routeServiceImpl.save(request);
        List<RouteSaveResponse> routeSaveResponseList = getRouteSaveResponseList(routeSaveResponse);

        var response = BaseResponse.<RouteSaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(routeSaveResponseList).successMessage("route created.").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByLocation")
    public ResponseEntity<Object> findRoutesByLocationQuery(@RequestParam("to") String to, @RequestParam("from") String from) {
        var route = routeServiceImpl.searchRoutesByLocation(to, from);
        var response = BaseResponse.<RouteSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(route).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable("id") Long id) {
        routeServiceImpl.deleteById(id);
    }

    private static List<RouteSaveResponse> getRouteSaveResponseList(RouteSaveResponse routeSaveResponse) {
        List<RouteSaveResponse> routeSaveResponseList = new ArrayList<>();
        routeSaveResponseList.add(routeSaveResponse);
        return routeSaveResponseList;
    }
}
