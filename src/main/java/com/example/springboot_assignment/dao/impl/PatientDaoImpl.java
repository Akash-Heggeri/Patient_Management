package com.example.springboot_assignment.dao.impl;
import com.example.springboot_assignment.dao.PatientDao;
import com.example.springboot_assignment.entity.PatientEntity;
import com.example.springboot_assignment.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static com.example.springboot_assignment.constants.LogConstants.*;

@Slf4j
@Repository
public class PatientDaoImpl implements PatientDao {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientEntity savePatient(PatientEntity patientEntity) {
        PatientEntity newPatientEntity = patientRepository.save(patientEntity);
        log.info(PATIENT_CREATED + newPatientEntity.getId());
        return newPatientEntity;
    }

    @Override
    public List<PatientEntity> getAllPatients() {
        List <PatientEntity> patientEntityList =  patientRepository.findAll();
        log.info(ALL_PATIENTS_DATA_FOUND);
        return patientEntityList;
    }

    @Override
    public Optional<PatientEntity> getPatientById(long id) {
        Optional<PatientEntity> existingPatientEntity = patientRepository.findById(id);
        log.info(PATIENTS_FOUND + id);
        return existingPatientEntity;
    }

    @Override
    public PatientEntity deletePatient(long id) {
        Optional<PatientEntity> patientEntityEx = patientRepository.findById(id);
        patientRepository.deleteById(id);
        log.info(PATIENT_DELETED + id);
        return patientEntityEx.get();
    }

    @Override
    public List<PatientEntity> getPatientByFirstname(String firstName) {
        List<PatientEntity> patientEntityList = patientRepository.findByFirstName(firstName);
        log.info(PATIENTS_FOUND_BY_FIRSTNAME + firstName);
        return patientEntityList;
    }

    @Override
    public List<PatientEntity> getPatientByFirstnameAndLastName(String firstName, String lastName) {
        List<PatientEntity> patientEntityList = patientRepository.findByFirstNameAndLastName(firstName,lastName);
        log.info(PATIENTS_FOUND_BY_FIRSTNAME_LASTNAME + firstName + " " + lastName);
        return patientEntityList;
    }
}
