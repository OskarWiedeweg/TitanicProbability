package com.example.TitanicProbability.repositorys;

import com.example.TitanicProbability.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PassengerRepository extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {

    @Query("SELECT SUM(fare) FROM Passenger WHERE passengerClass = ?1")
    Double getFare(Integer passengerClass);

}
