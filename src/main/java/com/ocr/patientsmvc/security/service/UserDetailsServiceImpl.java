package com.ocr.patientsmvc.security.service;

import com.ocr.patientsmvc.security.model.AppUser;
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
    private SecurityService securityService;

    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUsername(username);

        //programmation impérative
      /*  Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach( appRole -> {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appRole.getRoleName());
                    authorities.add(authority);
        });*/

        //programmation déclarative
        Collection<GrantedAuthority> authorities =
                appUser.getRoles()
                        .stream()
                        .map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName()))
                        .collect(Collectors.toList());

        User user = new User(appUser.getUsername(), appUser.getPassword(), authorities);

        return user;
    }
}
