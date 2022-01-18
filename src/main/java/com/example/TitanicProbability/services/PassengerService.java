package com.example.TitanicProbability.services;

import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.helper.SpecificationHelper;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerDTO> getAllPassengers(Integer page, Integer limit) {
        return getPassengerDTOS(page, limit, null);
    }

    public List<PassengerDTO> getAllPassengers(Integer page, Integer limit, Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        Specification<Passenger> specification = SpecificationHelper.getSpecification(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
        return getPassengerDTOS(page, limit, specification);
    }

    private List<PassengerDTO> getPassengerDTOS(Integer page, Integer limit, Specification<Passenger> specification) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, limit == null ? 50 : limit);
        Iterator<Passenger> passengers = passengerRepository.findAll(specification, pageable).iterator();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();

        passengers.forEachRemaining(passenger -> passengerDTOS.add(new PassengerDTO(passenger.getSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), passenger.getFare())));

        return passengerDTOS;
    }
}
