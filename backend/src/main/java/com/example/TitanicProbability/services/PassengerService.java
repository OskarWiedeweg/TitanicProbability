package com.example.TitanicProbability.services;

import com.example.TitanicProbability.dtos.AverageClassPriceDTO;
import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.dtos.PercentageDTO;
import com.example.TitanicProbability.helper.SpecificationHelper;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    EntityManager entityManager;

    public List<PassengerDTO> getAllPassengers(Integer page, Integer limit, Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        Specification<Passenger> specification = SpecificationHelper.getSpecification(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
        return getPassengerDTOS(page, limit, specification);
    }

    public Integer getAllPassengersFilteredCount(Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        Specification<Passenger> specification = SpecificationHelper.getSpecification(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
        return getPassengerDTOS(0, Integer.MAX_VALUE, specification).size();
    }

    private List<PassengerDTO> getPassengerDTOS(Integer page, Integer limit, Specification<Passenger> specification) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, limit == null ? 50 : limit);
        Iterator<Passenger> passengers = passengerRepository.findAll(specification, pageable).iterator();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();

        passengers.forEachRemaining(passenger -> passengerDTOS.add(new PassengerDTO(passenger.getId(), passenger.getSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), BigDecimal.valueOf(passenger.getFare()).setScale(2, RoundingMode.HALF_UP).doubleValue())));

        return passengerDTOS;
    }

    public PercentageDTO getPercentage(Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        long passengerCount = passengerRepository.count();


        Specification<Passenger> specification = SpecificationHelper.getSpecification(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);
        long queryCount = passengerRepository.count(specification);

        return new PercentageDTO(passengerCount, queryCount, BigDecimal.valueOf((queryCount * 1.0 / passengerCount) * 100).setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");
    }

    public AverageClassPriceDTO getAverageClassPrice(Integer passengerClass) {

        Double fare = passengerRepository.getFare(passengerClass);

        Long passengerCount = passengerRepository.count(Specification.where(SpecificationHelper.hasPassengerClass(passengerClass)));

        return new AverageClassPriceDTO(fare, passengerCount, new BigDecimal(fare / passengerCount).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    public PassengerDTO createPassenger(PassengerDTO passenger) {
        Passenger passengerInsertion = new Passenger(null, passenger.isSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), BigDecimal.valueOf(passenger.getFare()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        Passenger inserted = passengerRepository.save(passengerInsertion);
        passenger.setId(inserted.getId());
        return passenger;
    }

    public boolean deletePassenger(Long id) {
        try {
            passengerRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ignored) {
            return false;
        }
        return true;
    }
}
