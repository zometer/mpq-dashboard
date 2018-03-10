package com.zometer.app.mpqdashboard.dataimport;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zometer.app.mpqdashboard.dataimport.model.service.CharacterImportService;

@SpringBootApplication
public class DataImportConsoleApplication implements CommandLineRunner {

    Log log = LogFactory.getLog(getClass());

    @Autowired
    private CharacterImportService characterImportService;


    public static void main(String[] args) {
        SpringApplication.run(DataImportConsoleApplication.class, args);
    }

    @Override
    public void run(String...args) {
        log.info("Hello world: " + Arrays.asList(args));
        characterImportService.fetchCharactersFromWiki();
        log.info("complete");

    }
}
