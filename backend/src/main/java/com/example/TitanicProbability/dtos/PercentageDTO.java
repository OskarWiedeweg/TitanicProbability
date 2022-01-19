package com.example.TitanicProbability.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PercentageDTO {

    private Long allPassengers;
    private Long queryPassengers;
    private String percentage;

}
