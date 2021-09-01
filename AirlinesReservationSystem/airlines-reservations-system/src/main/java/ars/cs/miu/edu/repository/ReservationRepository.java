package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Flight;
import ars.cs.miu.edu.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReserveBy(String username);
     Reservation findByCode(String code);

}
