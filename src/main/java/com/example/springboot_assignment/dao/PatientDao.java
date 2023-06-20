package com.example.springboot_assignment.dao;
import com.example.springboot_assignment.entity.PatientEntity;


import java.util.List;
import java.util.Optional;

public interface PatientDao {
    PatientEntity savePatient(PatientEntity patientEntity);
    List<PatientEntity> getAllPatients();
    Optional<PatientEntity> getPatientById(long id);
    PatientEntity deletePatient(long id);
    List<PatientEntity> getPatientByFirstname(String firstName);
    List<PatientEntity> getPatientByFirstnameAndLastName(String firstName, String lastName);
}
