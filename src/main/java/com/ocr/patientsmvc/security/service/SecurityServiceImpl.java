package com.ocr.patientsmvc.security.service;

import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import com.ocr.patientsmvc.security.repository.AppRoleRepository;
import com.ocr.patientsmvc.security.repository.AppUserRepository;
import groovy.util.logging.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j //permettre à fournir un attribut log pour logger
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    //N'Oublie pas de changer le constructeur, ces paramètres, l'injection des dépendances, si on change PasswordEncoder
    public SecurityServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        AppUser newAppUser = appUserRepository.findByUsername(username);

        if (newAppUser != null)
            throw new RuntimeException("Role "+newAppUser+" already exists !!"); // throw new AppUserExistingException("Role "+roleName+" already exists !!");

        if(!password.equals(rePassword))
            throw new RuntimeException("Password not match !!"); // throw new PasswordNotMatchException("Password not match !!"); is better !!

        String hashedPassword = passwordEncoder.encode(password);
        //Au cas où, si id est un String, on rajoute(set) un userId, newAppUser.setAppUserId(UUID.randomUUID().toString())
        newAppUser = new AppUser();
        newAppUser.setUsername(username);
        newAppUser.setPassword(hashedPassword);
        newAppUser.setActive(true);

        AppUser savedAppUser = appUserRepository.save(newAppUser);

        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole newAppRole = appRoleRepository.findByRoleName(roleName);

        if (newAppRole != null)
            throw new RuntimeException("Role "+roleName+" already exists !!"); // throw new RoleExistingException("Role "+roleName+" already exists !!");

        newAppRole = new AppRole();

        newAppRole.setRoleName(roleName);
        newAppRole.setDescription(description);

        AppRole savedAppRole = appRoleRepository.save(newAppRole);

        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null)
            throw new RuntimeException("AppUser "+username+" doesn't exist !! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");

        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole == null)
            throw new RuntimeException("AppRole "+roleName+" doesn't exist !! "); //throw new AppRoleNotFoundException("AppRole "+appRole+" doesn't exist !!");

        appUser.getRoles().add(appRole);

        //appUserRepository.save(appUser); // C'est pas nécessaire !!
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null)
            throw new RuntimeException("AppUser "+username+" doesn't exist !! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");

        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole == null)
            throw new RuntimeException("AppRole "+roleName+" doesn't exist !! "); //throw new AppRoleNotFoundException("AppRole "+appRole+" doesn't exist !!");

        appUser.getRoles().remove(appRole);

        //appUserRepository.save(appUser); // C'est pas nécessaire !!

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null)
            throw new RuntimeException("AppUser "+username+" doesn't exist !! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");
        return appUser;
    }
}
