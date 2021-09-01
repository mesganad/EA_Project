package ars.cs.miu.edu.repository;

import ars.cs.miu.edu.models.Address;
import ars.cs.miu.edu.models.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByZip(String zip);
    void deleteByZip(String zip);
}
