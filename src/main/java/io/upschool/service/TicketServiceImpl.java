package io.upschool.service;

import io.upschool.Validation.CardValidation;
import io.upschool.Validation.PhoneValidation;
import io.upschool.dto.request.TicketSaveRequest;
import io.upschool.dto.response.TicketSaveResponse;
import io.upschool.entity.Flight;
import io.upschool.entity.Ticket;
import io.upschool.exception.BusinessException;
import io.upschool.repository.TicketRepository;
import io.upschool.service.Interface.FlightService;
import io.upschool.service.Interface.TicketService;
import io.upschool.utils.AirlineSystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final FlightService flightService;

    @Override
    public List<TicketSaveResponse> getAll() {
        var allTicket = ticketRepository.findAll();
        List<TicketSaveResponse> list = allTicket.stream().map(ticket -> getTicketSaveResponse(ticket)).toList();
        return list;
    }

    @Transactional
    @Override
    public TicketSaveResponse save(TicketSaveRequest request) {
        Flight flight = flightService.getReferenceById(request.getFlightId());

        var cardNumberWithOnlyDigit = convertCardNumberWithOnlyDigit(request.getCardNumber());
        CheckValidations(request, cardNumberWithOnlyDigit);
        String formattedCardNumber = maskCardNumber(cardNumberWithOnlyDigit, AirlineSystemConstant.MASKED_CARD_FORMAT);
        Ticket ticket = getTicket(request, flight, formattedCardNumber);
        Ticket savedTicket = ticketRepository.save(ticket);
        return getTicketSaveResponse(savedTicket);
    }

    private static void CheckValidations(TicketSaveRequest request, String cardNumberWithOnlyDigit) {
        if (PhoneValidation.IsNotValid(request.getPassengerPhoneNumber()))
            throw new BusinessException(AirlineSystemConstant.INVALID_PHONE_EXCEPTION);
        if (CardValidation.IsNotValid(cardNumberWithOnlyDigit))
            throw new BusinessException(AirlineSystemConstant.INVALID_CARD_NUMBER_EXCEPTION);
    }

    @Override
    public List<TicketSaveResponse> searchTicketsByPassengerNameOrSurname(String nameOrSurname) {
        var filteredTickets = ticketRepository.findAllByPassengerNameSurnameContainingIgnoreCase(nameOrSurname);
        List<TicketSaveResponse> list = filteredTickets.stream().map(ticket -> getTicketSaveResponse(ticket)).toList();
        return list;
    }

    @Override
    public TicketSaveResponse getTicketByTicketNumber(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumberContainingIgnoreCase(ticketNumber).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        ;
        return getTicketSaveResponse(ticket);
    }

    @Override
    public TicketSaveResponse findTicketById(Long id) {
        var ticket = ticketRepository.findById(id).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        return getTicketSaveResponse(ticket);
    }

    @Override
    public void deleteById(Long id) {
        var ticket = ticketRepository.findById(id).orElseThrow(() -> new BusinessException(AirlineSystemConstant.DATA_NOT_FOUND));
        ticket.setIsActive(false);
        ticketRepository.save(ticket);
    }

    private static Ticket getTicket(TicketSaveRequest request, Flight flight, String formattedCardNumber) {
        return Ticket.builder().flight(flight).passengerNameSurname(request.getPassengerNameSurname()).passengerPhoneNumber(request.getPassengerPhoneNumber()).CardNumber(formattedCardNumber).build();
    }

    private static TicketSaveResponse getTicketSaveResponse(Ticket ticket) {
        return TicketSaveResponse.builder().ticketNumber(ticket.getTicketNumber()).passengerNameSurname(ticket.getPassengerNameSurname()).CardNumber(ticket.getCardNumber()).destinationPlace(ticket.getFlight().getRoute().getDestinationPlace().getAirportName() + " - " + ticket.getFlight().getRoute().getDestinationPlace().getAddress()).departurePlace(ticket.getFlight().getRoute().getDeparturePlace().getAirportName() + " - " + ticket.getFlight().getRoute().getDeparturePlace().getAddress()).price(ticket.getFlight().getPrice()).flightDate(ticket.getFlight().getFlightDate()).AirlineCompanyName(ticket.getFlight().getAirlineCompany().getName()).build();
    }

    private static String maskCardNumber(String cardNumber, String mask) {
        int index = 0;
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '#') {
                maskedNumber.append(cardNumber.charAt(index));
                index++;
            } else if (c == 'x') {
                maskedNumber.append('*');
                index++;
            } else {
                maskedNumber.append(c);
            }
        }
        return maskedNumber.toString();
    }

    private static String convertCardNumberWithOnlyDigit(String cardNumber) {
        return cardNumber.replaceAll("\\D+", "");
    }
}
