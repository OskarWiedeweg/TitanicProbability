package com.example.TitanicProbability.controller;

import com.example.TitanicProbability.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Autowired
    private CsvService csvService;

    @GetMapping()
    public String importPassengers() {
        // TODO: enable auth
        csvService.importPassenger();

        return "Ok";
    }

}
