package io.upschool.repository;

import io.upschool.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketNumberContainingIgnoreCase(String ticketNumber);

    List<Ticket> findAllByPassengerNameSurnameContainingIgnoreCase(String name);

}
