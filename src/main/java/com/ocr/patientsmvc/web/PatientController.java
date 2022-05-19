package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.model.Patient;
import com.ocr.patientsmvc.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class PatientController {
    private PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping(path = "/index")
    public String patients(Model model){
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("listPatients",patients);
        return "patients";
    }

}
