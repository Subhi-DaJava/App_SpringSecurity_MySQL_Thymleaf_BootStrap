package com.ocr.patientsmvc.security.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Adil, Emet, Gulshen 1234
 */


@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long appUserId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(Long appUserId, String username, String password, boolean active, List<AppRole> roles) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRole> roles) {
        this.roles = roles;
    }
}
