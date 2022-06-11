package com.ocr.patientsmvc.web;

import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import com.ocr.patientsmvc.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppUsersAppRolesController {
    private static final Logger logger = LoggerFactory.getLogger("AppUsersAppRolesController.class");
    private SecurityService securityService;

    public AppUsersAppRolesController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(path = "/manager/users-roles")
    public String showUsersRoles(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword){
        logger.debug("This showUsersRoles method starts here");

        Page<AppUser> pageAppUsers = securityService.findByUsernameContains(keyword, PageRequest.of(page,size));
        Page<AppRole> pageAppRoles = securityService.findByRoleNameContains(keyword,PageRequest.of(page, size));

        model.addAttribute("pageUsers",pageAppUsers);
        model.addAttribute("pageRoles", pageAppRoles);
        model.addAttribute("pages", new int[pageAppUsers.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "appUsersAppRoles/users-roles";
    }
}
