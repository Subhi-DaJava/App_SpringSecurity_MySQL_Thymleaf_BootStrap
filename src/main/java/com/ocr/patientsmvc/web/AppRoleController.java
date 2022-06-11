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
                             @RequestParam(name = "roleName") String roleName,
                             @RequestParam(name = "description") String description,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "") String keyword){
        logger.debug("This method addAppRole starts here");

       securityService.saveNewRole(roleName, description);

       logger.info("model={}, roleNam={}, roelDescription={}", model, roleName, description);

        return "redirect:/manager/appRoles?page=" + page + "&keyword=" + keyword;
    }

    /**
     * Afficher le formulaire pour enregistrer a new App rôle et faire appel l'action @PostMapping(addAppRole) -> paramètre==name="paramètre"
     * @param model
     * @return
     */

    @GetMapping("/manager/formAddRole")
    public String saveAppRole(Model model){

        model.addAttribute("newRole", new AppRole());

        return "/appRole/formAppRoles";
    }

    @GetMapping("/manager/editAppRole")
    public String editAppRole(Model model, Long appRoleId, String keyword, int page){
        logger.debug("This method editAppRole starts here");
        AppRole appRole = securityService.findAppRoleByAppRoleId(appRoleId);

        if(appRole == null){
            logger.debug("This appRole doesn't exist in the DB by this appRoleI: "+ appRoleId);
            throw new RuntimeException("This appRoleId: " + appRoleId+" doesn't exist in the DB");
        }

        model.addAttribute("appRole", appRole);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);

        return "appRole/formEditAppRole";
    }

}
