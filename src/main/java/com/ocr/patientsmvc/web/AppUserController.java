package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import com.ocr.patientsmvc.security.service.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppUserController {
    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    @Autowired
    private SecurityService securityService;

    /**
     * Afficher tous les appUsers par page
     * @param model
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping(path = "/manager/appUsers")
    public String appUsers(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        logger.debug("This method starts here");

        Page<AppUser> pageAppUsers = securityService.findByUsernameContains(keyword, PageRequest.of(page, size));

        model.addAttribute("listAppUsers", pageAppUsers.getContent());
        model.addAttribute("pages", new int[pageAppUsers.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "/appUser/appUsers"; //c'est une vue
    }

    /**
     *  Form for adding the new app user
     * @param model
     * @return
     */
    @GetMapping("/manager/formAppUsers")
    public String formAppUsers(Model model) {
        model.addAttribute("appUser",new AppUser());
        return "appUser/formAppUsers";
    }

    /**
     * Save a new appUser
     * @param model
     * @param username
     * @param password
     * @param rePassword
     * @return
     */
    @PostMapping("/manager/saveAppUser")
    public String saveAppUser(Model model,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String rePassword){
   /*     if (!password.equals(rePassword)){
            return "passwordNotMatch";
        }
        if(username ==  null || !username.equals(securityService.loadUserByUsername(username))){
            return "usernameIssue";
        }*/
        logger.info("model={},username={},password={},rePassord={}", model,username,password,rePassword);

       securityService.saveNewUser(username, password, rePassword);

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
