package com.example.TitanicProbability.repositorys;

import com.example.TitanicProbability.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
