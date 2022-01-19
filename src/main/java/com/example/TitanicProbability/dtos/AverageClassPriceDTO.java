package com.example.TitanicProbability.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AverageClassPriceDTO {

    private Double totalClassPrice;
    private Long passengersInClass;
    private Double averageClassPrice;

}
