package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AirlineReposiory extends JpaRepository<Airline, Long> {
   Optional<Airline> findByCode(String code);
   void  deleteByCode(String code);
}
