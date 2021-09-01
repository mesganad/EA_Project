package ars.cs.miu.edu.controllers;


import ars.cs.miu.edu.jms.config.Mail;
import ars.cs.miu.edu.jms.config.service.MailService;
import ars.cs.miu.edu.jms.config.service.MailServiceImpl;
import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Reservation;
import ars.cs.miu.edu.models.Ticket;
import ars.cs.miu.edu.services.ReservationServiceImpl;
import ars.cs.miu.edu.services.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketServiceImpl ticketsService;
    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    MailServiceImpl mailService ;
    @Autowired
    JmsTemplate jmsTemplate;

    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        List<Ticket> ticketList = ticketsService.findAll();
        if(ticketList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticketList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getOneTicket(@PathVariable long id){
        Ticket ticket= ticketsService.findOne(id);
        if(ticket==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable long id, @RequestBody @Valid Ticket ticket){
        Ticket updatedTicket=null;
        Ticket tobeUpdateTicket =ticketsService.findOne(id);
        if(tobeUpdateTicket==null){
            updatedTicket= ticketsService.add(ticket);
        }else {
            ticket.setId(tobeUpdateTicket.getId());
            updatedTicket= ticketsService.update(ticket);
        }
        return  ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteTicket(@PathVariable long id){
        ticketsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Ticket> addTicket(@RequestBody @Valid Ticket ticket){
        Ticket addedTicket= ticketsService.add(ticket);


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedTicket.getId())
                .toUri();

        return ResponseEntity.created(uri).body(addedTicket);

    }
    @PostMapping("/reservations/confirm/{code}")
    public ResponseEntity<?> bookTicket(@PathVariable("code") String code){
        Reservation reservation=reservationService.findByReserveCode(code);
        String departure=reservation.getDeparture();
        String destination=reservation.getDestination();
        Flight flight=ticketsService.generateTicket(departure,destination).get(0);

        Ticket ticket=new Ticket();
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 7; i++) {
            str.append(random.nextInt(10));
        }
        String number= Instant.now().toEpochMilli()+str.toString();
        System.out.println(number);
        ticket.setNumber(number);
        ticket.setFlight(flight);
        ticket.setReservation(reservation);
        ticket.setFlightDate(flight.getDepartureDate());
        Ticket bookedTicket=ticketsService.add(ticket);

        String passenger="Dear  "+ reservation.getPassenger().getFirstName()+ "  "+reservation.getPassenger().getLastName();
        String flightNumber="\nFlight  number "+flight.getFlightNumber();
        String ticketId="\nTicket number "+ticket.getNumber();
        String date="\n Date  "+ticket.getFlightDate();
        String mailTo=reservation.getPassenger().getEmailAddress();
        Mail mail = new Mail();

        mail.setMailFrom("springeas@gmail.com");
        mail.setMailTo(mailTo);
        mail.setMailSubject("Ticket Booking");
        mail.setMailContent(passenger+flightNumber+ticketId+date);

        mailService.sendEmail(mail);

        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", mail);
        System.out.println("Finished putting the email in the queue");

        return ResponseEntity.ok(ticket);
    }
}


