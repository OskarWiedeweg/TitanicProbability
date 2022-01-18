package com.example.TitanicProbability.services;

import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerDTO> getAllPassengers(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page == null ? 1 : page, limit == null ? 50 : limit);
        Iterator<Passenger> passengers = passengerRepository.findAll(pageable).iterator();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();

        passengers.forEachRemaining(passenger -> passengerDTOS.add(new PassengerDTO(passenger.getSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), passenger.getFare())));

        return passengerDTOS;
    }

}
