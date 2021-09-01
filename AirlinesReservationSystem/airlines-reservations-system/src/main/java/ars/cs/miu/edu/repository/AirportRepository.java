package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AirportRepository extends JpaRepository<Airport, Long>{
    Optional<Airport> findByCode(String code);
    void deleteByCode(String code);
}
