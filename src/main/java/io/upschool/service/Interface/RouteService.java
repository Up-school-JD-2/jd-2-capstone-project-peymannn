package io.upschool.service.Interface;

import io.upschool.dto.request.RouteSaveRequest;
import io.upschool.dto.response.RouteSaveResponse;
import io.upschool.entity.Route;

import java.util.List;

public interface RouteService extends GenericService<RouteSaveRequest, RouteSaveResponse> {
    List<RouteSaveResponse> searchRoutesByLocation(String from, String to);

    Route getReferenceById(Long id);

    void deleteById(Long id);
}
