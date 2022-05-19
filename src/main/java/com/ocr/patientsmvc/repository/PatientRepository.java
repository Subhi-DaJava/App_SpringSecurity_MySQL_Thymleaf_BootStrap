package com.ocr.patientsmvc.repository;

import com.ocr.patientsmvc.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
