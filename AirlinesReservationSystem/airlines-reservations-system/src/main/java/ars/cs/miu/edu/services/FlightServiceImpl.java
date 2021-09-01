package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl  implements AirlinesService<Flight> {
    @Autowired
    private FlightRepository flightsRepository;

    @Override
    public List<Flight> findAll() {
        return flightsRepository.findAll();
    }

    @Override
    public Flight findOne(Long i) {
        return flightsRepository.findById(i).orElse(null);
    }

    @Override
    public Flight update(Flight t) {
        return  flightsRepository.save(t);
    }

    @Override
    public void delete(Long i) {

        flightsRepository.deleteById(i);
    }

    @Override
    public Flight add(Flight t) {
        return  flightsRepository.save(t);
    }
}
