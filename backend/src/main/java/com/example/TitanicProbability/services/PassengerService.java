package com.example.TitanicProbability.services;

import com.example.TitanicProbability.dtos.AverageClassPriceDTO;
import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.dtos.PercentageDTO;
import com.example.TitanicProbability.helper.SpecificationHelper;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        passengers.forEachRemaining(passenger -> passengerDTOS.add(new PassengerDTO(passenger.getId(), passenger.getSurvivedIndicator(), passenger.getPassengerClass(), passenger.getName(), passenger.getSex(), passenger.getAge(), passenger.getSiblingsAboard(), passenger.getParentsAboard(), passenger.getFare())));

        return passengerDTOS;
    }

    public PercentageDTO getPercentage(Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(Passenger.class)));

        Long passengerCount = entityManager.createQuery(cq).getSingleResult();


        Integer queryCount = getAllPassengersFilteredCount(survivedIndicator, passengerClass, name, sex, age, siblingsAboard, parentsAboard, fare);

        return new PercentageDTO(passengerCount, (long)queryCount, new BigDecimal((queryCount * 1.0 / passengerCount) * 100).setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");
    }

    public AverageClassPriceDTO getAverageClassPrice(Integer passengerClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cq = criteriaBuilder.createQuery(Double.class);
        Root<Passenger> root = cq.from(Passenger.class);
        cq.select(criteriaBuilder.sumAsDouble(root.get("fare")));
        cq.where(criteriaBuilder.equal(root.get("passengerClass"), passengerClass));

        Double fare = entityManager.createQuery(cq).getSingleResult();

        CriteriaBuilder cbPassengerCount = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cqPassengerCount = cbPassengerCount.createQuery(Long.class);
        Root<Passenger> rootPassengerCount = cqPassengerCount.from(Passenger.class);
        cqPassengerCount.select(cbPassengerCount.count(rootPassengerCount));
        cqPassengerCount.where(cbPassengerCount.equal(rootPassengerCount.get("passengerClass"), passengerClass));

        Long passengerCount= entityManager.createQuery(cqPassengerCount).getSingleResult();

        return new AverageClassPriceDTO(fare, passengerCount, new BigDecimal(fare / passengerCount).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
