package com.ocr.patientsmvc.security.model;

import javax.persistence.*;

@Entity
@Table(name = "app_role")
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long appRoleId;

    @Column(name = "role_name", unique = true)
    private String roleName;

    private String description;

    public AppRole() {
    }

    public AppRole(Long appRoleId, String roleName, String description) {
        this.appRoleId = appRoleId;
        this.roleName = roleName;
        this.description = description;
    }

    public Long getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(Long appRoleId) {
        this.appRoleId = appRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
