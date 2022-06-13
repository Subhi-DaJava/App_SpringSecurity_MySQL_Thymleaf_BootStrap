package com.ocr.patientsmvc.security.service;

import com.ocr.patientsmvc.security.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private SecurityService securityService;
    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Load user by username is working...");
        AppUser appUser = securityService.loadUserByUsername(username);

        if(appUser == null){
            logger.debug("This username " + username + "doesn't exist in DB");
            throw new UsernameNotFoundException("This user doesn't exist in DB");
        }

        //programmation impérative, pour chaque role on créé un objet de type SimpleGrantedAuthority, puis le rajouter dans la collection authorities
      /*  Collection<GrantedAuthority> authorities1 = new ArrayList<>();
        appUser.getRoles().forEach( appRole -> {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appRole.getRoleName());
                    authorities1.add(authority);
        });*/

        //programmation déclarative, map() -> pour chaque appUser
        Collection<GrantedAuthority> authorities =
                appUser.getRoles()
                        .stream()
                        .map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName()))
                        .collect(Collectors.toList());

        if(authorities.isEmpty()){
            logger.info("For the moment this user: "+ username + " has any role !!");
        }

        User user = new User(appUser.getUsername(), appUser.getPassword(), authorities);
        return user;
    }
}
