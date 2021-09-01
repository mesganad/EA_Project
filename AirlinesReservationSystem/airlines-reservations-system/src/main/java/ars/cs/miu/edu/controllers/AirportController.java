package ars.cs.miu.edu.controllers;

import ars.cs.miu.edu.exceptionHandler.CustomErrorType;
import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.models.Airport;
import ars.cs.miu.edu.services.AirlineServiceImpl;
import ars.cs.miu.edu.services.AirportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    @Autowired
    private AirportServiceImpl airportsService;

    @GetMapping
    public ResponseEntity<List<Airport>> findAll() {
        List<Airport> airportList = airportsService.findAll();
        if(airportList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airportList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getOneAirport(@PathVariable long id){
        Airport airport= airportsService.findOne(id);
        if(airport==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(airport);
    }

    //Find by Code
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getOneAirlineByCode(@PathVariable("code") String code){
        Airport airport= airportsService.findByCode(code);

        if(airport==null){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Airport with code = " + code + " is not available"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(airport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable long id, @RequestBody @Valid Airport airport){
        Airport updatedAirport;
        Airport tobeUpdateAirport=airportsService.findOne(id);
        if(tobeUpdateAirport==null){
            updatedAirport=airportsService.add(airport);
        }else{
           airport.setId(tobeUpdateAirport.getId());
            updatedAirport=airportsService.update(airport);
        }
        if(updatedAirport==null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(updatedAirport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteAirport(@PathVariable long id){
        airportsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("code/{code")
    public ResponseEntity<Object>  deleteAirport(@PathVariable String code){
        airportsService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Airport> addAirport(@RequestBody @Valid Airport airport){
        Airport addedAirport= airportsService.add(airport);


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedAirport.getId())
                .toUri();

        return ResponseEntity.created(uri).body(addedAirport);

    }
}


