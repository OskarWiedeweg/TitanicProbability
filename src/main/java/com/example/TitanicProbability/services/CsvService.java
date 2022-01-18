package com.example.TitanicProbability.services;

import com.example.TitanicProbability.helper.CsvHelper;
import com.example.TitanicProbability.models.Passenger;
import com.example.TitanicProbability.repositorys.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    PassengerRepository passengerRepository;

    public void importPassenger() {
        List<Passenger> passengers = CsvHelper.convertCsvToPassengers(getFileFromResourceAsStream("titanic.csv"));
        passengerRepository.saveAll(passengers);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

}
