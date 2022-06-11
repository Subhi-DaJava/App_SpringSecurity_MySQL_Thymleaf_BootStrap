package com.ocr.patientsmvc.security.repository;


import com.ocr.patientsmvc.security.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    // Retourner les AppUsers avec la page et le mot de recherche
    Page<AppUser> findByUsernameContains(String keyword, Pageable pageable);
}
