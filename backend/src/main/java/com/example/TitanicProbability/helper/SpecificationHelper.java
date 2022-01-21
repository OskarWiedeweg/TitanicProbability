package com.example.TitanicProbability.helper;

import com.example.TitanicProbability.models.Passenger;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationHelper {

    public static Specification<Passenger> getSpecification(Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        List<Specification<Passenger>> specifications = new ArrayList<>();

        if (survivedIndicator!=null) specifications.add(isSurvived(survivedIndicator));
        if (passengerClass!=null) specifications.add(hasPassengerClass(passengerClass));
        if (name!=null)specifications.add(hasName(name));
        if (sex!= null)specifications.add(hasSex(sex));
        if (age!=null)specifications.add(hasAge(age));
        if (siblingsAboard!=null)specifications.add(hasSiblingsAboard(siblingsAboard));
        if (parentsAboard!=null)specifications.add(hasParentsAboard(parentsAboard));
        if (fare!=null)specifications.add(hasFare(fare));

        if (specifications.size() == 1) {
            return specifications.get(0);
        }else if (specifications.size() == 0) {
            return null;
        }else {
            Specification<Passenger> start = Specification.where(specifications.get(0));
            for (int i = 1; i < specifications.size(); i++) {
                start = start.and(specifications.get(i));
            }
            return start;
        }
    }


    public static Specification<Passenger> isSurvived(boolean survived) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("survivedIndicator"), survived);
    }

    public static Specification<Passenger> hasPassengerClass(Integer passengerClass) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("passengerClass"), passengerClass);
    }

    public static Specification<Passenger> hasName(String name) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("name"), name);
    }

    public static Specification<Passenger> hasSex(String sex) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("sex"), sex);
    }

    public static Specification<Passenger> hasAge(Double age) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("age"), age);
    }

    public static Specification<Passenger> hasSiblingsAboard(Integer siblingsAboard) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("siblingsAboard"), siblingsAboard);
    }

    public static Specification<Passenger> hasParentsAboard(Integer parentsAboard) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("parentsAboard"), parentsAboard);
    }

    public static Specification<Passenger> hasFare(Double fare) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("fare"), fare);
    }
}
