package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Ticket;
import ars.cs.miu.edu.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TicketServiceImpl implements AirlinesService<Ticket> {
    @Autowired
    private TicketRepository ticketsRepository;

    @Override
    public List<Ticket> findAll() {
        return ticketsRepository.findAll();
    }

    @Override
    public Ticket findOne(Long i) {
        return ticketsRepository.findById(i).orElse(null);
    }

    @Override
    public Ticket update(Ticket t) {
        return  ticketsRepository.save(t);
    }

    @Override
    public void delete(Long i) {

        ticketsRepository.deleteById(i);
    }

    @Override
    public Ticket add(Ticket t) {
        return  ticketsRepository.save(t);
    }

    public List<Flight> generateTicket(String departure, String destination){
        return ticketsRepository.generateTicket(departure,destination);
    }
}

