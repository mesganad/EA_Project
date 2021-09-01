package ars.cs.miu.edu.controllers;

import ars.cs.miu.edu.models.*;
import ars.cs.miu.edu.services.AirlineServiceImpl;
import ars.cs.miu.edu.services.AirportServiceImpl;
import ars.cs.miu.edu.services.PersonServiceImpl;
import ars.cs.miu.edu.services.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;
    private AirportServiceImpl airportsService;
    private ReservationServiceImpl reservationService;

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> findAllPersons() {
        List<Person> personList = personService.findAll();
        if (personList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personList);
    }


    //usecases1:  list of airlines flying out of an airport (search by airport three letter code)
//    @PreAuthorize("PASSENGER OR ADMIN")
    @GetMapping("/passengers/airlines/{airportCode}")
    public ResponseEntity<List<?>> findAllAirlinesByCode(@PathVariable String airportCode) {
        List<Airline> airlineList = personService.getAirlinesByCode(airportCode);
        System.out.println("Checking airlines size"+ airlineList.size());
        if (airlineList.isEmpty()) {
            System.out.println("inside 0 size beacause of empty if condition"+ airlineList.size());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airlineList);
    }

   // usecase2:  list of flights between a departure and destination for a date
    @GetMapping("/passengers/flights/{date}/{depCity}/{arrivalCity}")
    public ResponseEntity<List<?>> findAllFlightsByDepartureAndDestinationDate(@PathVariable String date, @PathVariable String depCity, @PathVariable String arrivalCity ) {
        LocalDate localDate = LocalDate.parse(date);
        List<Flight> flightList = personService.getFlightsByDateAndDepCityAndArrivalCity(localDate,depCity,arrivalCity);
        if (flightList.isEmpty()) {
            System.out.println("inside 0 size beacause of empty if condition"+ flightList.size());
            return ResponseEntity.notFound().build();
        }
        System.out.println(" outside of if condition"+ flightList.size());
        return ResponseEntity.ok(flightList);
    }

    // usecase3:  List of own Reservations
    @GetMapping("/passengers/{id}/reservations")
    public ResponseEntity<List<?>> viewReservations(@PathVariable Long id) {
        List<Reservation> reservationList = personService.getAllReservations(id);
        if (reservationList.isEmpty()) {
            System.out.println("inside 0 size beacause of empty if condition"+ reservationList.size());
            return ResponseEntity.notFound().build();
        }
        System.out.println(" outside of if condition"+ reservationList.size());
        return ResponseEntity.ok(reservationList);
    }

    //usecase4: view details of a Reservation
    @GetMapping("/passengers/{id}/reservations/{reservationid}")
    public ResponseEntity<?> viewReservations(@PathVariable Long id,@PathVariable Long reservationid) {
       Reservation reservation = personService.getAReservations(id, reservationid);
        if (reservation==null) {
            System.out.println("inside 0 size beacause of empty if condition");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    //usecase6 Cancel A Reservation


    // Passenger CRUD
    @GetMapping("/passengers")
    public ResponseEntity<List<Person>> findAllPassengers() {
        List<Person> passengerList = personService.findAll();
        passengerList=passengerList.stream().filter(l->l.getRole()==Role.PASSENGER)
                .collect(Collectors.toList());

        if (passengerList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerList);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Person> getOnePassenger(@PathVariable long id) {
        Passenger passenger = (Passenger) personService.findOne(id);
        if (passenger == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passenger);
    }

    @PostMapping("/passengers")
    public ResponseEntity<Person> addPerson(@RequestBody @Valid Passenger passenger) {
        passenger.setRole(Role.PASSENGER);
        Passenger addedPassenger = (Passenger) personService.add(passenger);
        return ResponseEntity.ok(addedPassenger);
    }

    @PutMapping("passengers/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable long id, @RequestBody @Valid Passenger passenger) {
        Passenger updatedPassenger = null;
        Passenger tobeUpdatePassenger = (Passenger) personService.findOne(id);
        if (tobeUpdatePassenger == null) {
            updatedPassenger = (Passenger) personService.add(passenger);
        } else {
            passenger.setId(tobeUpdatePassenger.getId());
            updatedPassenger = (Passenger) personService.update(passenger);
        }
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("passengers/{id}")
    public ResponseEntity<Object> deletePassenger(@PathVariable long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Admin CRUD
    @GetMapping("/admins")
    public ResponseEntity<List<Person>> findAllAdmins() {
        List<Person> adminList = personService.findAll();
        adminList=   adminList.stream().filter(l->l.getRole()==Role.ADMIN).collect(Collectors.toList());

        if (adminList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adminList);
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getOneAdmin(@PathVariable long id) {
        Admin admin = (Admin) personService.findOne(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/admins")
    public ResponseEntity<Admin> addadmin(@RequestBody @Valid Admin admin) {
        admin.setRole(Role.PASSENGER);
        Admin addedAdmin = (Admin) personService.add(admin);
        return ResponseEntity.ok(addedAdmin);
    }

    @PutMapping("admins/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable long id, @RequestBody @Valid Admin admin) {
        Admin updatedAdmin = null;
        Admin tobeUpdateAdmin = (Admin) personService.findOne(id);
        if (tobeUpdateAdmin == null) {
            updatedAdmin = (Admin) personService.add(admin);
        } else {
            admin.setId(tobeUpdateAdmin.getId());
            updatedAdmin = (Admin) personService.update(admin);
        }
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("admins/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Agent CRUD
    @GetMapping("/agents")
    public ResponseEntity<List<Person>> findAllAgents() {
        List<Person> agentList = personService.findAll();
        agentList=agentList.stream().filter(l->l.getRole()==Role.AGENT)
                .collect(Collectors.toList());
        if (agentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agentList);
    }

    @GetMapping("/agents/{id}")
    public ResponseEntity<Agent> getOneAgent(@PathVariable long id) {
        Agent agent = (Agent) personService.findOne(id);
        if (agent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agent);
    }

    @PostMapping("/agents")
    public ResponseEntity<Agent> addAgent(@RequestBody @Valid Agent agent) {
        agent.setRole(Role.PASSENGER);
        Agent addedAgent = (Agent) personService.add(agent);
        return ResponseEntity.ok(addedAgent);
    }

    @PutMapping("agents/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable long id, @RequestBody @Valid Agent agent) {
        Agent updatedAgent = null;
        Agent tobeUpdateAgent = (Agent) personService.findOne(id);
        if (tobeUpdateAgent == null) {
            updatedAgent = (Agent) personService.add(agent);
        } else {
            agent.setId(tobeUpdateAgent.getId());
            updatedAgent = (Agent) personService.update(agent);
        }
        return ResponseEntity.ok(updatedAgent);
    }

    @DeleteMapping("agents/{id}")
    public ResponseEntity<Object> deleteAgent(@PathVariable long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}