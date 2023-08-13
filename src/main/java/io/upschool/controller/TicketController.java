package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.request.TicketSaveRequest;
import io.upschool.dto.response.TicketSaveResponse;
import io.upschool.service.TicketServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketServiceImpl ticketServiceImpl;

    @GetMapping("/tickets")
    public ResponseEntity<Object> getTickets() {
        var ticketSaveResponse = ticketServiceImpl.getAll();
        var response = BaseResponse.<TicketSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(ticketSaveResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ticket")
    public ResponseEntity<Object> createTicket(@Valid @RequestBody TicketSaveRequest request) {
        var ticketSaveResponse = ticketServiceImpl.save(request);
        List<TicketSaveResponse> ticketSaveResponseList = getTicketSaveResponseList(ticketSaveResponse);

        var response = BaseResponse.<TicketSaveResponse>builder().status(HttpStatus.CREATED.value()).isSuccess(true).dataList(ticketSaveResponseList).successMessage("ticket purchase completed.").build();
        return ResponseEntity.ok(response);
    }

    private static List<TicketSaveResponse> getTicketSaveResponseList(TicketSaveResponse ticketSaveResponse) {
        List<TicketSaveResponse> ticketSaveResponseList = new ArrayList<>();
        ticketSaveResponseList.add(ticketSaveResponse);
        return ticketSaveResponseList;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findTicketsByPassengerNameOrSurname(@RequestParam("nameOrSurname") String nameOrSurname) {
        var ticket = ticketServiceImpl.searchTicketsByPassengerNameOrSurname(nameOrSurname);
        var response = BaseResponse.<TicketSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(ticket).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTicketById(@PathVariable("id") Long id) {
        var ticketSaveResponse = ticketServiceImpl.findTicketById(id);
        List<TicketSaveResponse> ticketSaveResponseList = getTicketSaveResponseList(ticketSaveResponse);
        var response = BaseResponse.<TicketSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(ticketSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{ticketNumber}")
    public ResponseEntity<Object> findTicketByTicketNumber(@PathVariable("ticketNumber") String ticketNumber) {
        var ticketSaveResponse = ticketServiceImpl.getTicketByTicketNumber(ticketNumber);
        List<TicketSaveResponse> ticketSaveResponseList = getTicketSaveResponseList(ticketSaveResponse);
        var response = BaseResponse.<TicketSaveResponse>builder().status(HttpStatus.OK.value()).isSuccess(true).dataList(ticketSaveResponseList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketServiceImpl.deleteById(id);
    }
}
