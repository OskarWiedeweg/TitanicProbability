package com.example.TitanicProbability.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Passenger {

    @Id
    private Long id;

    private boolean survivedIndicator;
    private Integer passengerClass;
    private String name;
    private String sex;
    private Integer age;
    private Integer siblingsAboard;
    private Integer parentsAboard;
    private Double fare;

}
