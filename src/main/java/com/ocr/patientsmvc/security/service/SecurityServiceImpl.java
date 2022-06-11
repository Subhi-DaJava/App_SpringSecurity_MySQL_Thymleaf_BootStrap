package com.ocr.patientsmvc.security.service;

import com.ocr.patientsmvc.exception.AppUserNotExistingException;
import com.ocr.patientsmvc.exception.appUserExistingException;
import com.ocr.patientsmvc.security.model.AppRole;
import com.ocr.patientsmvc.security.model.AppUser;
import com.ocr.patientsmvc.security.repository.AppRoleRepository;
import com.ocr.patientsmvc.security.repository.AppUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
 //permettre à fournir un attribut log pour logger
public class SecurityServiceImpl implements SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

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
        logger.debug("This method starts here");
        AppUser newAppUser = appUserRepository.findByUsername(username); // username est unique

        if (newAppUser != null) {
            logger.info("This appUser is already in the DB !");
            //Create a costume exception
            throw new appUserExistingException("AppUser: " + username + " already exists !!"); // throw new AppUserExistingException("Role "+roleName+" already exists !!");
        }

        if (!password.equals(rePassword)) {
            logger.debug(rePassword + " does not match with " + password);
            // Ideal, c'est de créer une exception personnalisée
            throw new RuntimeException("Password not match !!"); // throw new PasswordNotMatchException("Password not match !!"); is better !!
        }

        logger.info("PasswordEncoder is working...");
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
        logger.debug("This method starts here !");
        AppRole newAppRole = appRoleRepository.findByRoleName(roleName); // roleName est unique

        if (newAppRole != null) {
            logger.info("This role: " + roleName +" is already in the DB");
            throw new RuntimeException("Role: " + roleName + " already exists !!"); // throw new RoleExistingException("Role "+roleName+" already exists !!");
        }

        newAppRole = new AppRole();

        newAppRole.setRoleName(roleName);
        newAppRole.setDescription(description);

        AppRole savedAppRole = appRoleRepository.save(newAppRole);

        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        logger.debug("This method starts here");
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            logger.debug("No this user " + username + " in the DB");
            throw new RuntimeException("AppUser: " + username + " doesn't exist !! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");
        }

        AppRole appRole = appRoleRepository.findByRoleName(roleName);

        if (appRole == null) {
            logger.debug("No this role: " + roleName + " in the DB");
            throw new RuntimeException("AppRole " + roleName + " doesn't exist !! "); //throw new AppRoleNotFoundException("AppRole "+appRole+" doesn't exist !!");
        }
        logger.info("This role " +roleName+ " is added to this username "+username);
        appUser.getRoles().add(appRole);

        //appUserRepository.save(appUser); // C'est pas nécessaire, grâce à l'annotation @Transactionnel, commit / rollback
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        logger.debug("This method starts here.");

        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            logger.debug("No this user: " + username + " in the DB");
            throw new RuntimeException("AppUser " + username + " doesn't exist !! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");
        }

        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) {
            logger.debug("No this role: " + roleName + " in the DB");
            throw new RuntimeException("AppRole " + roleName + " doesn't exist !! "); //throw new AppRoleNotFoundException("AppRole "+appRole+" doesn't exist !!");
        }

        appUser.getRoles().remove(appRole);

        //appUserRepository.save(appUser); // C'est pas nécessaire, grâce à l'annotation @Transactionnel

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        logger.debug("This method starts here");
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            logger.debug("This user: " + username + " doesn't exist in the DB");
            throw new AppUserNotExistingException("AppUser " + username + " doesn't exist in the DB!! "); //throw new AppUserNotFoundException("AppUser "+username+" doesn't exist !!");
        }
        return appUser;
    }

    @Override
    public Page<AppUser> findByUsernameContains(String keyword, Pageable pageable) {
        return appUserRepository.findByUsernameContains(keyword, pageable);
    }

    @Override
    public Page<AppRole> findByRoleNameContains(String keyword, Pageable pageable) {
        return appRoleRepository.findByRoleNameContains(keyword, pageable);
    }

    @Override
    public void deleteAppUserById(Long appUserId) {

        appUserRepository.deleteById(appUserId);
    }

    /*
        cette méthode n'est pas encore employée
     */
    @Override
    public void deleteAppRoleById(Long appRoleId) {

        appRoleRepository.deleteById(appRoleId);
    }

    //Find the AppRole by appRoleId
    @Override
    public AppRole findAppRoleByAppRoleId(Long appRoleId) {
        logger.debug("This method findAppRoleByAppRoleId starts here");

        AppRole appRole = appRoleRepository.findById(appRoleId).orElse(null);

        if(appRole == null){
            logger.debug("Not found appRole by this appRoleId: " + appRoleId);
            throw new RuntimeException("AppRole by this appRoleId " + appRoleId + " doesn't exist in the DB");
        }

        return appRole;
    }

    /**
     * Créer et mettre à jour un AppRole
     * @param appRole
     * @return
     */
    @Override
    public AppRole saveAppRole(AppRole appRole) {
        logger.debug("This method saveAppRole starts here");
        AppRole candidatUpdate = findAppRoleByAppRoleId(appRole.getAppRoleId());
        if(candidatUpdate !=  null){
            candidatUpdate.setRoleName(appRole.getRoleName());
            candidatUpdate.setDescription(appRole.getDescription());
            logger.info("Updated successfully");
            return candidatUpdate;
        }

        if(appRole == null){
            logger.debug("AppRole should not be null");
            throw new RuntimeException("AppRole should not be null");
        }

        AppRole newAppRole = new AppRole();

        newAppRole.setRoleName(appRole.getRoleName());
        newAppRole.setDescription(appRole.getDescription());

        AppRole savedAppRole = appRoleRepository.save(newAppRole);
        logger.info("AppRole: " + savedAppRole + " is saved");

        return savedAppRole;
    }
}
