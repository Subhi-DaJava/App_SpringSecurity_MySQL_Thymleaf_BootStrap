package com.ocr.patientsmvc.web;

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
        logger.debug("This show appUsers method starts here");

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
        logger.info("model={},username={},password={},rePassord={}", model,username,password,rePassword);

       securityService.saveNewUser(username, password, rePassword);

        return "redirect:/manager/appUsers";
    }

    @PostMapping("/manager/addRoleToAppUser")
    public String addRoleToAppUser(String username, String roleName){
        securityService.addRoleToUser(username,roleName);
        return "redirect:/manager/appUsers";
    }

    @PostMapping("/manager/removeRoleFromAppUser")
    public String removeRoleFromAppUser(String username, String roleName){
        securityService.removeRoleFromUser(username,roleName);
        return "redirect:/";
    }

    /**
     * Afficher le formulaire pour rajouter un rôle à un AppUser en même temps faire apple l'action @PostMapping pour remplir et renvoyer le formulaire
     * @param model
     * @return
     */
    @GetMapping("manager/formAddRoleToAppUser")
    public String addRoleToAppUser(Model model){

        model.addAttribute("addRoleToUser", new AppUser());

        return "/appUser/formAddRoleToAppUser";
    }

    /**
     * Rajouter un rôle à un appUser
     * @param model
     * @param username
     * @param roleName
     * @return
     */

    @PostMapping("/manager/addRoleToUser")
    public String addRoleToUser(Model model,
                                @RequestParam String username,
                                @RequestParam String roleName){
        logger.debug("This addRoleToUser method starts here");

        securityService.addRoleToUser(username, roleName);

        logger.info("username={}, roleName={}", username, roleName);

        return "redirect:/manager/users-roles";
    }

    /**
     * Delete a AppUser
     * @param appUserId
     * @param keyword
     * @param page
     * @return
     */
    @GetMapping("/manager/deleteAppUser")
    public String deleteAppUser(Long appUserId, String keyword, int page){

        securityService.deleteAppUserById(appUserId);

        return "redirect:/manager/appUsers?page=" + page + " &keyword=" + keyword;
    }

}
