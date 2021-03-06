package com.example.TitanicProbability.controller;

import com.example.TitanicProbability.dtos.AverageClassPriceDTO;
import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.dtos.PercentageDTO;
import com.example.TitanicProbability.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/all")
    public List<PassengerDTO> getAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Boolean survivedIndicator, @RequestParam(required = false) Integer passengerClass, @RequestParam(required = false) String name, @RequestParam(required = false) String sex, @RequestParam(required = false) Double age, @RequestParam(required = false) Integer siblingsAboard, @RequestParam(required = false) Integer parentsAboard, @RequestParam(required = false) Double fare) {
        return passengerService.getAllPassengers(page, limit, survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
    }

    @GetMapping("/percentage")
    public PercentageDTO getPercentage(@RequestParam(required = false) Boolean survivedIndicator, @RequestParam(required = false) Integer passengerClass, @RequestParam(required = false) String name, @RequestParam(required = false) String sex, @RequestParam(required = false) Double age, @RequestParam(required = false) Integer siblingsAboard, @RequestParam(required = false) Integer parentsAboard, @RequestParam(required = false) Double fare) {
        return passengerService.getPercentage(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
    }

    @GetMapping("/price")
    public AverageClassPriceDTO getAverageClassPrice(@RequestParam Integer passengerClass) {
        return passengerService.getAverageClassPrice(passengerClass);
    }

    @PostMapping()
    public PassengerDTO createPassenger(@RequestBody PassengerDTO passengerDTO) {
        return passengerService.createPassenger(passengerDTO);
    }

    @PatchMapping()
    public PassengerDTO updatePassenger() {
        return null;
    }

    @DeleteMapping()
    @CrossOrigin(origins = {"*"})
    public ResponseEntity<?> deletePassenger(@RequestParam Long id) {
        if (passengerService.deletePassenger(id)) {
            return ResponseEntity.accepted().body(true);
        }
        return ResponseEntity.notFound().build();
    }

}
