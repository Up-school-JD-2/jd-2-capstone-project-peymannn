package io.upschool.config;

import io.upschool.dto.request.*;
import io.upschool.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Component
public class DBinitConfig {
    @Autowired
    private AirportServiceImpl airportService;
    @Autowired
    private AirlineCompanyServiceImpl airlineCompanyService;
    @Autowired
    private RouteServiceImpl routeService;

    @Autowired
    private FlightServiceImpl flightService;
    @Autowired
    private TicketServiceImpl ticketService;

    @Transactional
    @PostConstruct
    private void postConstruct() {
        AirportSaveRequest airport1 = new AirportSaveRequest("Prof. Dr. Aziz SANCAR Havalimanı", "Mardin");
        AirportSaveRequest airport2 = new AirportSaveRequest("Adnan Menderes Havalimanı", "Izmir");
        airportService.save(airport1);
        airportService.save(airport2);

        AirlineCompanySaveRequest airlineCompany1 = new AirlineCompanySaveRequest("Pegasus Airline", "pegasus@gmail.com");
        AirlineCompanySaveRequest airlineCompany2 = new AirlineCompanySaveRequest("Turkish Airline", "turkish@gmail.com");
        airlineCompanyService.save(airlineCompany1);
        airlineCompanyService.save(airlineCompany2);

        RouteSaveRequest routeSaveRequest1 = new RouteSaveRequest(1L, 2L);
        RouteSaveRequest routeSaveRequest2 = new RouteSaveRequest(2L, 1L);
        routeService.save(routeSaveRequest1);
        routeService.save(routeSaveRequest2);

        FlightSaveRequest flightSaveRequest1 = new FlightSaveRequest(Timestamp.valueOf("2027-11-15 15:30:14.332"), 2000.00, 1L, 1L);
        FlightSaveRequest flightSaveRequest2 = new FlightSaveRequest(Timestamp.valueOf("2027-11-17 15:30:14.332"), 3000.00, 2L, 2L);
        flightService.save(flightSaveRequest1);
        flightService.save(flightSaveRequest2);

        TicketSaveRequest ticket1 = new TicketSaveRequest(1L, "PEYMAN oter", "05070088389", "1234123412341234");
        TicketSaveRequest ticket2 = new TicketSaveRequest(2L, "PEYMAN Atali", "05070088389", "1234123412341255");
        ticketService.save(ticket1);
        ticketService.save(ticket2);
    }


}
