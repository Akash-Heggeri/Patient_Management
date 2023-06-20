package com.example.springboot_assignment.service;

import com.example.springboot_assignment.utility.StatusResponse;
import com.example.springboot_assignment.model.PatientModel;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
	StatusResponse savePatient(PatientModel patientModel);
	StatusResponse getAllPatients();
	StatusResponse getPatientById(String id);
	StatusResponse updatePatient(PatientModel patientModel);
	StatusResponse deletePatient(String id);
	StatusResponse getPatientByName(String firstName);
	StatusResponse getPatientByNameAndLastName(String firstName, String lastName);
}
