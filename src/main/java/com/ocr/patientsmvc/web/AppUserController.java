package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.model.Patient;
import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import com.ocr.patientsmvc.security.service.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @GetMapping("/manager/formAppUsers")
    public String formPatients(Model model) {
        model.addAttribute("appUser",new AppUser());
        return "formAppUsers";
    }


    @Autowired
    private SecurityService securityService;

    @PostMapping("/manager/saveAppUser")
    public String saveAppUser(Model model,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String rePassword){
        if (!password.equals(rePassword)){
            return "passwordNotMatch";
        }

        AppUser newAppUser = securityService.saveNewUser(username, password, rePassword);

        return "redirect:/";
    }

    @PostMapping("/manager/addRole")
    public String addRole(String roleName, String roleDescription){

        AppRole newAppRole = securityService.saveNewRole(roleName, roleDescription);

        return "redirect:/";
    }

    @PostMapping("/manager/addRoleToAppUser")
    public String addRoleToAppUser(String username, String roleName){
        securityService.addRoleToUser(username,roleName);
        return "redirect:/";
    }

    @PostMapping("/manager/removeRoleFromAppUser")
    public String removeRoleFromAppUser(String username, String roleName){
        securityService.removeRoleFromUser(username,roleName);
        return "redirect:/";
    }

}
