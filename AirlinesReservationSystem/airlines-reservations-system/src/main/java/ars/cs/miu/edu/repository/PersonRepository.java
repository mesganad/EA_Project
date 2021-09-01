package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Person;
import ars.cs.miu.edu.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {

    Optional<Person> findByUsername(String username);
    //usecase1
    @Query("select f.airline from Flight f where f.departureAirport.code=:code")
    List<Airline> queryByCode(@Param("code") String code);

    //usecase2
    @Query("select f from Flight f where f.departureDate=:date and f.departureAirport.name =:depname and f.arrivalAirport.name =:arrname")
   List<Flight> queryForFlightsByDepartureAndDestinationDate(@Param("date") LocalDate departureDate, @Param("depname") String depname, @Param("arrname") String arrivalname);

    //usercase3
    @Query("select r from Reservation r join r.passenger p where p.id=:id")
    List<Reservation> getReservationsById(@Param("id")Long id);
    //usecase4
    @Query("select r from Reservation r join r.passenger p where p.id=:id and r.id= :reservationid")
    Reservation getAReservationByPersonAndReservationId(@Param("id")Long id, @Param("reservationid") Long reservationid);

}
