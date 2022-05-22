package com.ocr.patientsmvc;

import com.ocr.patientsmvc.model.Patient;
import com.ocr.patientsmvc.repository.PatientRepository;
import com.ocr.patientsmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

/*    @Bean    //injecter PatientRepository
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null, "Alain", new Date(), false,552));
            patientRepository.save(
                    new Patient(null, "Jean", new Date(), false,102));
            patientRepository.save(
                    new Patient(null, "Maxime", new Date(), true,352));
            patientRepository.save(
                    new Patient(null, "July", new Date(), true,678));

            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }*/

    @Bean
    CommandLineRunner saveAppUsers(SecurityService securityService){
        return args -> {
            //en commentaire, déjà enregistrés
            /*securityService.saveNewUser("Adil","1234","1234");
            securityService.saveNewUser("Emet","1234","1234");
            securityService.saveNewUser("Gulshen","1234","1234");*/

            //en commentaire, déjà enregistrés
           /* securityService.saveNewRole("ADMIN","Gestion tout");
            securityService.saveNewRole("USER", "Consultation uniquement");

            //en commentaire, déjà enregistrés
            securityService.addRoleToUser("Adil","ADMIN");
            securityService.addRoleToUser("Adil","USER");
            securityService.addRoleToUser("Emet","USER");
            securityService.addRoleToUser("Gulshen","USER");*/

        };
    }

}
