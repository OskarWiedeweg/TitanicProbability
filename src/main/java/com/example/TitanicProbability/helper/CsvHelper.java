package com.example.TitanicProbability.helper;

import com.example.TitanicProbability.models.Passenger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvHelper {

    private static String TYPE = "text/csv";
    private static String[] HEADER = { "Survived", "Pclass", "Name", "Sex", "Age", "Siblings/Spouses Aboard", "Parents/Children Aboard", "Fare" };


    public static boolean isCSV(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Passenger> convertCsvToPassengers(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Passenger> passengers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Passenger passenger = new Passenger(null,
                        csvRecord.get("Survived").equals("1"),
                        Integer.valueOf(csvRecord.get("Pclass")),
                        csvRecord.get("Name"),
                        csvRecord.get("Sex"),
                        Double.valueOf(csvRecord.get("Age")),
                        Integer.valueOf(csvRecord.get("Siblings/Spouses Aboard")),
                        Integer.valueOf(csvRecord.get("Parents/Children Aboard")),
                        Double.valueOf(csvRecord.get("Fare")));

                passengers.add(passenger);
            }

            return passengers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


}
