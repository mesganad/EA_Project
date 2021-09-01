package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT f from Flight f where f.departureAirport.name=:departure and f.arrivalAirport.name=:arrivalAirport and f.departureDate>current_date()")
    public List<Flight> generateTicket(@Param("departure") String departure, @Param("arrivalAirport") String destination);
}
