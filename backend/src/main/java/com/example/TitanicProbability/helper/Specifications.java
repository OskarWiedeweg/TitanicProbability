package com.example.TitanicProbability.helper;

import com.example.TitanicProbability.models.Passenger;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {

    static Specification<Passenger> isSurvived(boolean survived) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("survivedIndicator"), survived);
    }

    static Specification<Passenger> hasPassengerClass(Integer passengerClass) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("passengerClass"), passengerClass);
    }

    static Specification<Passenger> hasName(String name) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("name"), name);
    }

    static Specification<Passenger> hasSex(String sex) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("sex"), sex);
    }

    static Specification<Passenger> hasAge(Double age) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("age"), age);
    }

    static Specification<Passenger> hasSiblingsAboard(Integer siblingsAboard) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("siblingsAboard"), siblingsAboard);
    }

    static Specification<Passenger> hasParentsAboard(Integer parentsAboard) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("parentsAboard"), parentsAboard);
    }

    static Specification<Passenger> hasFare(Double fare) {
        return (passenger, cq, cb) -> cb.equal(passenger.get("fare"), fare);
    }

}
