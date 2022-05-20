package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.model.Patient;
import com.ocr.patientsmvc.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PatientController {
    //l'injection de dépendance
    private PatientRepository patientRepository;
    //l'injection avec Constructeur
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size){
        Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "patients"; //c'est une vue
    }

}
