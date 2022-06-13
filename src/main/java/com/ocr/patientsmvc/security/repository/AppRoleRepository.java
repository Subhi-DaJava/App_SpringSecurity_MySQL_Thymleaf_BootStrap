package com.ocr.patientsmvc.security.repository;

import com.ocr.patientsmvc.security.model.AppRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByRoleName(String roleName);
    // Retourner les AppRoles avec la page et le mot de recherche
    Page<AppRole> findByRoleNameContains(String keyword, Pageable pageable);

    AppRole findAppRoleByRoleName(String roleName);

}
