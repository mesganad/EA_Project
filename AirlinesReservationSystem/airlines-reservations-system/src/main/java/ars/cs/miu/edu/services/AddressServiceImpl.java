package ars.cs.miu.edu.services;

import ars.cs.miu.edu.models.Address;
import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl  implements AirlinesService<Address> {
    @Autowired
    private AddressRepository addresssRepository;

    @Override
    public List<Address> findAll() {
        return addresssRepository.findAll();
    }

    @Override
    public Address findOne(Long i) {
        return addresssRepository.findById(i).orElse(null);
    }

    @Override
    public Address update(Address t) {
        return  addresssRepository.save(t);
    }

    @Override
    public void delete(Long i) {

        addresssRepository.deleteById(i);
    }

    @Override
    public Address add(Address t) {
        return  addresssRepository.save(t);
    }

    public Address findByZip(String zip){
        return addresssRepository.findByZip(zip).orElse(null);
    }
    public void deleteByZip(String zip){
         addresssRepository.deleteByZip(zip);
    }
}

