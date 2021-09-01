package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.models.Airport;
import ars.cs.miu.edu.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AirportServiceImpl  implements AirlinesService<Airport> {
    @Autowired
    private AirportRepository airportsRepository;

    @Override
    public List<Airport> findAll() {
        return airportsRepository.findAll();
    }

    @Override
    public Airport findOne(Long i) {
        return airportsRepository.findById(i).orElse(null);
    }

    @Override
    public Airport update(Airport t) {
        return  airportsRepository.save(t);
    }

    @Override
    public void delete(Long i) {

        airportsRepository.deleteById(i);
    }

    @Override
    public Airport add(Airport t) {
        return  airportsRepository.save(t);
    }

    public Airport findByCode(String code){
        return airportsRepository.findByCode(code).orElse(null);
    }
    public void deleteByCode(String code){
        airportsRepository.deleteByCode(code);
    }
}

