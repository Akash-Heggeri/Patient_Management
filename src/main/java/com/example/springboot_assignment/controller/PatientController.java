package com.example.springboot_assignment.controller;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import com.example.springboot_assignment.utility.StatusResponse;
import com.example.springboot_assignment.model.PatientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springboot_assignment.service.PatientService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.springboot_assignment.constants.LogConstants.*;

@Slf4j
@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;


	@PostMapping("/addpatient")
	public ResponseEntity<StatusResponse> savePatient(@Valid @RequestBody PatientModel patientModel){
		StatusResponse statusResponse = new StatusResponse();
		ResponseEntity<StatusResponse> responseEntity;

		try {
			statusResponse = patientService.savePatient(patientModel);
			if(statusResponse.getResponseObject() == null){
				responseEntity = new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
				log.error(PATIENT_NOT_CREATED + patientModel.getFirstName() + " " + patientModel.getLastName());
			}else {
				responseEntity = new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.CREATED);
				log.info(PATIENT_CREATED + statusResponse.getResponseObject());
			}
		}catch (ConstraintViolationException e){
			List<String> errorMsgs = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
			statusResponse.setStatus(ERROR);
			statusResponse.setResponseMessage(errorMsgs);
			log.error(INTERNAL_ERROR + errorMsgs);
			responseEntity = new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
		} catch (Exception e){
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}

	
	@GetMapping("/")
	public ResponseEntity<StatusResponse> getAllPatients(){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();

		try{
			statusResponse = patientService.getAllPatients();
			responseEntity =  new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
			log.info(ALL_PATIENTS_DATA_FOUND + statusResponse.getResponseObject());
		}catch (Exception e){
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}


	@GetMapping("/filterfirstname")
	public ResponseEntity<StatusResponse> getPatientByFirstName(@RequestParam String firstName){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();

		try{
			statusResponse = patientService.getPatientByName(firstName);
			responseEntity =  new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
			log.info(PATIENTS_FOUND_BY_FIRSTNAME + firstName + "\n" + statusResponse.getResponseObject());
		}catch (Exception e){
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/filterfirstnameandlastname")
	public ResponseEntity<StatusResponse> getPatientByFirstLastName(@RequestParam String firstName, @RequestParam String lastName){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();

		try{
			statusResponse = patientService.getPatientByNameAndLastName(firstName,lastName);
			responseEntity =  new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
			log.info(PATIENTS_FOUND_BY_FIRSTNAME_LASTNAME + firstName + " " + lastName + "\n" + statusResponse.getResponseObject());
		}catch (Exception e){
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}

		return responseEntity;
	}

	
	@GetMapping("/filterid")
	public ResponseEntity<StatusResponse> getPatientById(@RequestParam String patientId){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();

	    try {
			statusResponse = patientService.getPatientById(patientId);
			responseEntity = new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.OK);
			log.info(PATIENTS_FOUND + patientId + "\n" + statusResponse.getResponseObject());
	    }catch (Exception e) {
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}


	@PutMapping("/updatepatient")
	public ResponseEntity<StatusResponse> updatePatient(@Valid @RequestBody PatientModel patientModel){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();
		try {
			statusResponse = patientService.updatePatient(patientModel);
			responseEntity = new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.OK);
			log.info(PATIENT_UPDATED + statusResponse.getResponseObject());
		}catch (ConstraintViolationException e){
			List<String> errorMsgs = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
			statusResponse.setStatus(ERROR);
			statusResponse.setResponseMessage(errorMsgs);
			log.error(INTERNAL_ERROR + errorMsgs);
			responseEntity = new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.OK);
		} catch (Exception e) {
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("/deletepatient")
	public ResponseEntity<StatusResponse> deletePatient(@RequestParam String patientId){
		ResponseEntity<StatusResponse> responseEntity;
		StatusResponse statusResponse = new StatusResponse();

	    try {
			statusResponse = patientService.deletePatient(patientId);
			responseEntity = new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.OK);
			log.info(PATIENT_DELETED + statusResponse.getResponseObject());
	    }catch (Exception e) {
			log.error(INTERNAL_ERROR + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
		return responseEntity;
	}
}