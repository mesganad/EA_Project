package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Reservation;
import ars.cs.miu.edu.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationServiceImpl  implements AirlinesService<Reservation> {
    @Autowired
    private ReservationRepository reservationsRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationsRepository.findAll();
    }

    @Override
    public Reservation findOne(Long i) {
        return reservationsRepository.findById(i).orElse(null);
    }

    @Override
    public Reservation update(Reservation t) {
        return  reservationsRepository.save(t);
    }

    @Override
    public void delete(Long i) {
        reservationsRepository.deleteById(i);
    }
    @Override
    public Reservation add(Reservation t) {
        return  reservationsRepository.save(t);
    }

    public  List<Reservation> findByReserveBy(String username){
        return reservationsRepository.findByReserveBy(username);
    }

    public  Reservation findByReserveCode(String code){
        return reservationsRepository.findByCode(code);
    }
}

