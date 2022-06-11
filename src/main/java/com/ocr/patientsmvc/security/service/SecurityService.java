package com.ocr.patientsmvc.security.service;


import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecurityService {

    // Enregistrer un nouvel user dans la bdd
    AppUser saveNewUser(String username, String password, String rePassword);

    // Sauvegarder un nouveau rôle dans la bdd
    AppRole saveNewRole(String roleName, String description);

    // Affecter/assigner/rajouter le rôle à un appUser
    void addRoleToUser(String username, String roleName);

    // Enlever/supprimer un rôle d'un utilisateur
    void removeRoleFromUser(String username, String roleName);

    // Chercher/trouver un user par son username
    AppUser loadUserByUsername(String username);

    Page<AppUser> findByUsernameContains(String keyword, Pageable pageable);

    Page<AppRole> findByRoleNameContains(String keyword, Pageable pageable);

    void deleteAppUserById(Long appUserId);

    //Wait to creat
    void deleteAppRoleById(Long appRoleId);

    //Find by appRoleId

    AppRole findAppRoleByAppRoleId(Long appRoleId);

    AppRole saveAppRole(AppRole appRole);
}
