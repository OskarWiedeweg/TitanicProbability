package com.example.TitanicProbability.services;

import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerDTO> getAllPassengers() {
        Iterator<Passenger> passengers = passengerRepository.findAll().iterator();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();

        passengers.forEachRemaining(passenger -> passengerDTOS.add(new PassengerDTO(passenger.getSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), passenger.getFare())));

        return passengerDTOS;
    }

}
