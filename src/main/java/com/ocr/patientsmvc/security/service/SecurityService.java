package com.ocr.patientsmvc.security.service;


import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;

public interface SecurityService {

    AppUser saveNewUser(String username, String password, String rePassword);
    AppRole saveNewRole(String roleName, String description);

    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);

    AppUser loadUserByUsername(String username);


}
