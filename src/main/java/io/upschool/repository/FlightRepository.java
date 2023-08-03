package io.upschool.repository;

import io.upschool.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByAirlineCompanyNameContainingIgnoreCase(String name);

    List<Flight> findAllByRouteDestinationPlaceAddressAndRouteDeparturePlaceAddress(String to, String from);


}
