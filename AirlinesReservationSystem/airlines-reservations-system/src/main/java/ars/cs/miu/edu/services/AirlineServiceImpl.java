package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.repository.AirlineReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AirlineServiceImpl implements AirlinesService<Airline> {
    @Autowired
    private AirlineReposiory airlinesRepository;

    @Override
    public List<Airline>  findAll() {
        return airlinesRepository.findAll();
    }

    @Override
    public Airline findOne(Long i) {
        return airlinesRepository.findById(i).orElse(null);
    }

    @Override
    public Airline update(Airline t) {
        return  airlinesRepository.save(t);
    }

    @Override
    public void delete(Long i) {

        airlinesRepository.deleteById(i);
    }

    @Override
    public Airline add(Airline t) {
        return  airlinesRepository.save(t);
    }

    public  Airline findByCode(String code){
        return airlinesRepository.findByCode(code).orElse(null);
    }
    public void deleteByCode(String code){
      airlinesRepository.deleteByCode(code);
    }
}
