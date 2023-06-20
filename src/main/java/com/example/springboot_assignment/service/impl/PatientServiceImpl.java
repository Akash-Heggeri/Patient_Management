package com.example.springboot_assignment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.springboot_assignment.dao.PatientDao;
import com.example.springboot_assignment.utility.StatusResponse;
import com.example.springboot_assignment.model.PatientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.springboot_assignment.entity.PatientEntity;
import com.example.springboot_assignment.service.PatientService;

import static com.example.springboot_assignment.constants.LogConstants.*;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService{
	@Autowired
	private PatientDao patientDao;

	@Override
	public StatusResponse savePatient(PatientModel patientModel) {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();

		try {
			PatientEntity createdPatientEntity = patientDao.savePatient(new PatientEntity(patientModel));
			if (createdPatientEntity == null) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_CREATED);
				log.error(PATIENT_NOT_CREATED);
			} else {
				PatientModel createdPatientModel = new PatientModel(createdPatientEntity);
				statusResponse.setStatus(SUCCESS);
				statusResponse.setResponseObject(createdPatientModel);
				msgResponsesList.add(PATIENT_CREATED);
				log.info(PATIENT_CREATED + statusResponse.getResponseObject());
			}
		}catch (DataIntegrityViolationException exception){
			msgResponsesList.add(PATIENT_ALREADY_EXIST + patientModel.getFirstName() + " " + patientModel.getLastName());
			statusResponse.setStatus(ERROR);
			log.error(PATIENT_ALREADY_EXIST + patientModel.getFirstName() + " " + patientModel.getLastName());
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}


	@Override
	public StatusResponse getAllPatients() {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();
		List<PatientEntity> fetchedPatientEntitiesList = patientDao.getAllPatients();

		if (fetchedPatientEntitiesList.isEmpty()) {
			statusResponse.setStatus(ERROR);
			msgResponsesList.add(PATIENTS_NOT_FOUND);
			log.warn(PATIENTS_NOT_FOUND);
		} else {
			List<PatientModel> patientModelsList;
			patientModelsList = fetchedPatientEntitiesList.stream().
								map(PatientModel::new).collect(Collectors.toList());

			log.info(ENTITY_MODEL_CONVERSION + patientModelsList);
			statusResponse.setStatus(SUCCESS);
			statusResponse.setResponseObject(patientModelsList);
			msgResponsesList.add(ALL_PATIENTS_DATA_FOUND);
			log.info(ALL_PATIENTS_DATA_FOUND + statusResponse.getResponseObject());
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}

	@Override
	public StatusResponse getPatientById(String patientId) {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();

		try {
			long id = Long.parseLong(patientId);
			Optional<PatientEntity> fetchedOptionalPatientEntity = patientDao.getPatientById(id);

			if (fetchedOptionalPatientEntity.isEmpty()) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_FOUND + "id :"+id);
				log.error(PATIENT_NOT_FOUND + "id :" +id);
			} else {
				PatientModel patientModel = new PatientModel(fetchedOptionalPatientEntity.get());
				log.info(ENTITY_MODEL_CONVERSION + patientModel);
				statusResponse.setResponseObject(patientModel);
				statusResponse.setStatus(SUCCESS);
				msgResponsesList.add(PATIENTS_FOUND + patientId);
				log.info(PATIENTS_FOUND + patientId + "\n" + statusResponse.getResponseObject());
			}
		}catch (NumberFormatException exception){
			msgResponsesList.add(INVALID_PATIENT_ID + patientId);
			statusResponse.setStatus(ERROR);
			log.error(INVALID_PATIENT_ID + patientId);
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}

	@Override
	public StatusResponse updatePatient(PatientModel patientModel) {

		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();
		PatientEntity updatedPatientEntity;
		Optional<PatientEntity> existingOptionalPatientEntity = patientDao.getPatientById(patientModel.getId());

		try {
			if (existingOptionalPatientEntity.isEmpty()) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_FOUND + "id :" + patientModel.getId());
				log.error(PATIENT_NOT_FOUND+ "id :" + patientModel.getId());
			} else {
				log.info("Patient Entity Conversion" + new PatientEntity(patientModel));
				updatedPatientEntity = patientDao.savePatient(new PatientEntity(patientModel));
				log.info("Patient Entity Conversion" + updatedPatientEntity);
				if (updatedPatientEntity == null) {
					statusResponse.setStatus(ERROR);
					msgResponsesList.add(PATIENT_NOT_UPDATED);
					log.error(PATIENT_NOT_UPDATED);
				} else {
					PatientModel updatedPatientModel = new PatientModel(updatedPatientEntity);
					log.info(ENTITY_MODEL_CONVERSION);
					statusResponse.setResponseObject(updatedPatientModel);
					statusResponse.setStatus(SUCCESS);
					msgResponsesList.add(PATIENT_UPDATED);
					log.info(PATIENT_UPDATED + statusResponse.getResponseObject());
				}
			}
		}catch (DataIntegrityViolationException exception){
			msgResponsesList.add(PATIENT_ALREADY_EXIST + patientModel.getFirstName() + " " + patientModel.getLastName());
			statusResponse.setStatus(ERROR);
			log.error(PATIENT_ALREADY_EXIST + patientModel.getFirstName() + " " + patientModel.getLastName());
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}

	@Override
	public StatusResponse deletePatient(String patientId) {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();

		try {
			long id = Long.parseLong(patientId);
			Optional<PatientEntity> existingOptionalPatientEntity = patientDao.getPatientById(id);

			if (existingOptionalPatientEntity.isEmpty()) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_FOUND + "id :" + patientId);
				log.error(PATIENT_NOT_FOUND + "id :" + patientId);
			} else {
				PatientEntity deletedPatientEntity = patientDao.deletePatient(id);
				if (deletedPatientEntity == null) {
					statusResponse.setStatus(ERROR);
					msgResponsesList.add(PATIENT_NOT_DELETED);
					log.error(PATIENT_NOT_DELETED);
				} else {
					PatientModel patientModel = new PatientModel(deletedPatientEntity);
					log.info(ENTITY_MODEL_CONVERSION);
					statusResponse.setResponseObject(patientModel);
					statusResponse.setStatus(SUCCESS);
					msgResponsesList.add(PATIENT_DELETED);
					log.info(PATIENT_DELETED + id);
				}
			}

		}catch (NumberFormatException exception){
			msgResponsesList.add(INVALID_PATIENT_ID + patientId);
			statusResponse.setStatus(ERROR);
			log.error(INVALID_PATIENT_ID + patientId);
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;

	}

	@Override
	public StatusResponse getPatientByName(String firstName) {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();

		if(firstName.isEmpty()){
			statusResponse.setStatus(ERROR);
			msgResponsesList.add(FIRST_NAME_EMPTY);
			log.error(FIRST_NAME_EMPTY + firstName);
		}else {
			List<PatientEntity> fetchedPatientEntitiesList = patientDao.getPatientByFirstname(firstName);
			List<PatientModel> patientModelsList;

			if (fetchedPatientEntitiesList.isEmpty()) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_FOUND + "first name: " + firstName);
				log.error(PATIENT_NOT_FOUND + "first name: " + firstName);
			} else {
				patientModelsList = fetchedPatientEntitiesList.stream()
						.map(PatientModel::new).collect(Collectors.toList());
				log.info(ENTITY_MODEL_CONVERSION);
				statusResponse.setResponseObject(patientModelsList);
				statusResponse.setStatus(SUCCESS);
				msgResponsesList.add(PATIENTS_FOUND_BY_FIRSTNAME + firstName);
				log.info(PATIENTS_FOUND_BY_FIRSTNAME + statusResponse.getResponseObject());
			}
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}

	@Override
	public StatusResponse getPatientByNameAndLastName(String firstName, String lastName) {
		StatusResponse statusResponse = new StatusResponse();
		List<String> msgResponsesList = new ArrayList<>();
		List<PatientModel> patientModelsList;

		if(firstName.isEmpty() || lastName.isEmpty()){
			statusResponse.setStatus(ERROR);
			if(firstName.isEmpty()) {
				msgResponsesList.add(FIRST_NAME_EMPTY + firstName);
				log.error(FIRST_NAME_EMPTY + firstName);
			}
			if(lastName.isEmpty()){
				msgResponsesList.add(LAST_NAME_EMPTY + lastName);
				log.error(LAST_NAME_EMPTY + lastName);
			}
		}else {
			List<PatientEntity> fetchedPatientEntitiesList = patientDao.getPatientByFirstnameAndLastName(firstName, lastName);
			if (fetchedPatientEntitiesList.isEmpty()) {
				statusResponse.setStatus(ERROR);
				msgResponsesList.add(PATIENT_NOT_FOUND + "first name: " + firstName + " & lastname: " + lastName);
				log.error(PATIENT_NOT_FOUND + "first name: " + firstName + " & lastname: " + lastName);
			} else {
				patientModelsList = fetchedPatientEntitiesList.stream()
						.map(PatientModel::new).collect(Collectors.toList());

				log.info(ENTITY_MODEL_CONVERSION);
				statusResponse.setResponseObject(patientModelsList);
				statusResponse.setStatus(SUCCESS);
				msgResponsesList.add(PATIENTS_FOUND_BY_FIRSTNAME_LASTNAME + firstName + " & " + lastName);
			}
		}

		statusResponse.setResponseMessage(msgResponsesList);
		return statusResponse;
	}

}
