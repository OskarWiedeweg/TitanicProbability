package com.example.TitanicProbability.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerDTO {

    private Long id;
    private boolean survivedIndicator;
    private Integer passengerClass;
    private String name;
    private String sex;
    private Double age;
    private Integer siblingsAboard;
    private Integer parentsAboard;
    private Double fare;

}
