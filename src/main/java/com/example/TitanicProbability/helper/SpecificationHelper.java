package com.example.TitanicProbability.helper;

import com.example.TitanicProbability.models.Passenger;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationHelper {

    public static Specification<Passenger> getSpecification(Boolean survivedIndicator, Integer passengerClass, String name, String sex, Double age, Integer siblingsAboard, Integer parentsAboard, Double fare) {
        List<Specification<Passenger>> specifications = new ArrayList<>();

        if (survivedIndicator!=null) specifications.add(Specifications.isSurvived(survivedIndicator));
        if (passengerClass!=null) specifications.add(Specifications.hasPassengerClass(passengerClass));
        if (name!=null)specifications.add(Specifications.hasName(name));
        if (sex!= null)specifications.add(Specifications.hasSex(sex));
        if (age!=null)specifications.add(Specifications.hasAge(age));
        if (siblingsAboard!=null)specifications.add(Specifications.hasSiblingsAboard(siblingsAboard));
        if (parentsAboard!=null)specifications.add(Specifications.hasParentsAboard(parentsAboard));
        if (fare!=null)specifications.add(Specifications.hasFare(fare));

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
}
