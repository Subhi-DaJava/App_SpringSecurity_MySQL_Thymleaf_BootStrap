package com.ocr.patientsmvc;

import com.ocr.patientsmvc.model.Patient;
import com.ocr.patientsmvc.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    @Bean    //injecter PatientRepository
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null, "Alain", new Date(), false,5));
            patientRepository.save(
                    new Patient(null, "Jean", new Date(), false,10));
            patientRepository.save(
                    new Patient(null, "Maxime", new Date(), true,35));
            patientRepository.save(
                    new Patient(null, "July", new Date(), true,67));

            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }

}
