package ars.cs.miu.edu.services;


import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Person;
import ars.cs.miu.edu.models.Reservation;
import ars.cs.miu.edu.repository.PersonRepository;
import ars.cs.miu.edu.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonServiceImpl<T extends Person>  implements AirlinesService<T> {
    @Autowired
    private PersonRepository personsRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<T> findAll() {
        return personsRepository.findAll();
    }

    @Override
    public T findOne(Long i) {
        return (T)personsRepository.findById(i).orElse(null);
    }

    @Override
    public T update(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return  (T)personsRepository.save(person);
    }

    @Override
    public void delete(Long id) {

        personsRepository.deleteById(id);
    }
    @Override
    public T add(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return  (T)personsRepository.save(person);
    }

    public List<Airline> getAirlinesByCode(String code){
        return personsRepository.queryByCode(code);
    }

    public List<Flight> getFlightsByDateAndDepCityAndArrivalCity(LocalDate depDate, String depCity, String arrivalCity){
        return personsRepository.queryForFlightsByDepartureAndDestinationDate(depDate,depCity,arrivalCity);
    }

    public List<Reservation> getAllReservations(Long id){
        return personsRepository.getReservationsById(id);
    }

    public Reservation getAReservations(Long id, Long reservationId){
        return personsRepository.getAReservationByPersonAndReservationId(id, reservationId);
    }

    public boolean addReservation(Reservation reservation){
        reservationRepository.save(reservation);
        return true;
    }


    public Person findByUsername(String username){
        return (Person) personsRepository.findByUsername(username).orElse(null);
    }
}
