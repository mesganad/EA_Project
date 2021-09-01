package ars.cs.miu.edu.controllers;

import ars.cs.miu.edu.exceptionHandler.CustomErrorType;
import ars.cs.miu.edu.models.Airline;
import ars.cs.miu.edu.services.AirlineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {
    @Autowired
    private AirlineServiceImpl airlinesService;


    @GetMapping
    public ResponseEntity<List<Airline>> findAll() {
        List<Airline> airlineList = airlinesService.findAll();
        if(airlineList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airlineList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAirline(@PathVariable long id){
        Airline airline= airlinesService.findOne(id);
        if(airline==null){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Airline with number = " + id + " is not available"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(airline);
    }

    //Find by Code
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getOneAirlineByCode(@PathVariable("code") String code){
        Airline airline= airlinesService.findByCode(code);

        if(airline==null){
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Airline with code = " + code + " is not available"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(airline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airline> updateAirline(@PathVariable long id, @RequestBody @Valid Airline airline){
        Airline updatedAirline=null;
        Airline tobeUpdateAirline =airlinesService.findOne(id);
        if(tobeUpdateAirline==null){
            updatedAirline= airlinesService.add(airline);
        }else {
            airline.setId(tobeUpdateAirline.getId());
            updatedAirline= airlinesService.update(airline);
        }
        return  ResponseEntity.ok(updatedAirline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteAirline(@PathVariable long id){
        airlinesService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("code/{code}")
    public ResponseEntity<Object>  deleteAirlineByCode(@PathVariable String code){
        airlinesService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Airline> addAirline(@RequestBody @Valid Airline airline){
        Airline addedAirline= airlinesService.add(airline);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedAirline.getId())
                .toUri();

        return ResponseEntity.created(uri).body(addedAirline);
    }
}
