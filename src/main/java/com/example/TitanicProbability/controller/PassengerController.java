package com.example.TitanicProbability.controller;

import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping()
    public List<PassengerDTO> getAll() {
        return passengerService.getAllPassengers();
    }

}
