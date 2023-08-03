package io.upschool.repository;

import io.upschool.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByDestinationPlaceAddressAndDeparturePlaceAddress(String to, String from);
}
