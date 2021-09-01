package ars.cs.miu.edu.controllers;

import ars.cs.miu.edu.exceptionHandler.CustomErrorType;
import ars.cs.miu.edu.models.*;
import ars.cs.miu.edu.models.Reservation;
import ars.cs.miu.edu.services.PersonServiceImpl;
import ars.cs.miu.edu.services.ReservationServiceImpl;
import ars.cs.miu.edu.services.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {
    @Autowired
    private ReservationServiceImpl reservationsService;
 @Autowired
 private PersonServiceImpl personService;
 @Autowired
 private TicketServiceImpl ticketService;
    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservationList = reservationsService.findAll();
        if(reservationList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getOneReservation(@PathVariable long id){
        Reservation reservation= reservationsService.findOne(id);
        if(reservation==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservation);
    }

    @GetMapping("byAgent/{username}")
    public ResponseEntity<?> getAllReservationByAgent(@PathVariable String username){
        List<Reservation> reservationList= reservationsService.findByReserveBy(username);
        if(reservationList==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable long id, @RequestBody @Valid Reservation reservation){
        if (reservation.getReserveAmount()>0){
            reservation.setStatus(Status.RESERVED);
        }
        else if (reservation.getType().equalsIgnoreCase("cancel")){
            reservation.setStatus(Status.CANCELED);
        }
        Reservation updatedReservation= null;
        Reservation tobeUpdateReservation =reservationsService.findOne(id);
        if(tobeUpdateReservation==null){
            updatedReservation= reservationsService.add(reservation);
        }else {
            reservation.setId(tobeUpdateReservation.getId());
            updatedReservation= reservationsService.update(reservation);
        }
        return  ResponseEntity.ok(updatedReservation);
    }
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Reservation> confirm(@PathVariable long id, @RequestBody @Valid Reservation reservation){
        if (reservation.getReserveAmount()>0){
            reservation.setStatus(Status.RESERVED);
        }
        else if (reservation.getType().equalsIgnoreCase("cancel")){
            reservation.setStatus(Status.CANCELED);
        }
        Reservation updatedReservation= null;
        Reservation tobeUpdateReservation =reservationsService.findOne(id);
        if(tobeUpdateReservation==null){
            updatedReservation= reservationsService.add(reservation);
        }else {
            reservation.setId(tobeUpdateReservation.getId());
            updatedReservation= reservationsService.update(reservation);
        }
        return  ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteReservation(@PathVariable long id){
        reservationsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addReservation(@RequestBody @Valid Reservation reservation,@PathVariable String username){
        List<Flight> flights=ticketService.generateTicket(reservation.getDeparture(),reservation.getDestination());
        if(flights.size()==0){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("The is not flight from " + reservation.getDeparture()+ " to"+reservation.getDestination()), HttpStatus.NOT_FOUND);
        }
        Passenger passenger= (Passenger) personService.findByUsername(username);
        reservation.setPassenger(passenger);
        reservation.setStatus(Status.RESERVED);
        Reservation addedReservation= reservationsService.add(reservation);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedReservation.getId())
                .toUri();
        return ResponseEntity.created(uri).body(addedReservation);
    }

}

