package io.upschool.service.Interface;

import io.upschool.dto.request.TicketSaveRequest;
import io.upschool.dto.response.TicketSaveResponse;

import java.util.List;

public interface TicketService extends GenericService<TicketSaveRequest, TicketSaveResponse> {
    List<TicketSaveResponse> searchTicketsByPassengerNameOrSurname(String nameOrSurname);

    TicketSaveResponse getTicketByTicketNumber(String ticketNumber);

    TicketSaveResponse findTicketById(Long id);

    void deleteById(Long id);
}