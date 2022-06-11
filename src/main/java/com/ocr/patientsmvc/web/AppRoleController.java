package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppRoleController {
    private static final Logger logger = LoggerFactory.getLogger(AppRoleController.class);

    private SecurityService securityService;

    public AppRoleController(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Afficher tous les appRôles par page
     * @param model
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/manager/appRoles")
    public String showAppRoles(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size",defaultValue = "5") int size,
                               @RequestParam(name = "keyword",defaultValue = "") String keyword){
        logger.debug("This method starts here");

        Page<AppRole> pageAppRoles = securityService.findByRoleNameContains(keyword, PageRequest.of(page, size));

        model.addAttribute("listAppRoles", pageAppRoles.getContent());
        model.addAttribute("pages", new int[pageAppRoles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "/appRole/appRoles";
    }

    /**
     * Enregistrer un rôle dans la BDD
     * @param model
     * @param roleName
     * @param description
     * @return
     */
    @PostMapping("/manager/addRole")
    public String addAppRole(Model model,
                             @RequestParam String roleName,
                             @RequestParam String description){
        logger.debug("This method addAppRole starts here");

       securityService.saveNewRole(roleName, description);

       logger.info("model={}, roleNam={}, roelDescription={}", model, roleName, description);

        return "redirect:/";
    }

    /**
     * Afficher le formulaire pour enregistrer a new App rôle et faire appel l'action @PostMapping(addAppRole) -> paramètre==name="paramètre"
     * @param model
     * @return
     */

    @GetMapping("/manager/formAddRole")
    public String addAppRole(Model model){

        model.addAttribute("newRole", new AppRole());

        return "/appRole/formAppRoles";
    }

}