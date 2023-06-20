package com.example.springboot_assignment.controller;

import com.example.springboot_assignment.utility.StatusResponse;
import com.example.springboot_assignment.entity.*;
import com.example.springboot_assignment.model.PatientModel;
import com.example.springboot_assignment.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.springboot_assignment.constants.LogConstants.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    @Autowired
    private ObjectMapper objectMapper;
    private PatientEntity patientEntity;
    private PatientModel patientModel;
    private ArrayList<AddressesEntity> addressesEntityArrayList;
    private ArrayList<AllergiesEntity> allergiesEntitiesList;
    private ArrayList<ContactEntity> contactEntityArrayList;
    private ArrayList<PrescriptionsEntity> prescriptionsEntityArrayList;
    private ClinicalEntity clinicalEntity;
    private InsuranceEntity insuranceEntity;
    private PrescriberEntity prescriberEntity;

    private void createAddressEntityList(){
        AddressesEntity addressesEntity = new AddressesEntity();
        addressesEntity.setAddressType("Home");
        addressesEntity.setLine("Line 1");
        addressesEntity.setCity("Delhi");
        addressesEntity.setPinCode("431136");
        addressesEntityArrayList = new ArrayList<>();
        addressesEntityArrayList.add(addressesEntity);
    }

    private void createAllergyEntityList(){
        AllergiesEntity allergiesEntity = new AllergiesEntity();
        allergiesEntity.setCategory("Cough");
        allergiesEntity.setClinicalStatus("Active");
        allergiesEntity.setSeverityString("Medium");
        allergiesEntitiesList = new ArrayList<>();
        allergiesEntitiesList.add(allergiesEntity);
    }

    private void createClinicalEntity(){
        clinicalEntity = new ClinicalEntity();
        clinicalEntity.setWeight(75);
        clinicalEntity.setHeight(175);
        createAllergyEntityList();
        clinicalEntity.setAllergies(allergiesEntitiesList);
    }

    private void createContactEntityList(){
        ContactEntity contactEntityPhone = new ContactEntity();
        contactEntityPhone.setContactType("Phone");
        contactEntityPhone.setValue("9559031306");
        ContactEntity contactEntityEmail = new ContactEntity();
        contactEntityEmail.setContactType("Email");
        contactEntityEmail.setValue("khemraj99b");
        contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(contactEntityPhone);
        contactEntityArrayList.add(contactEntityEmail);
    }

    private void createInsuranceEntity(){
        insuranceEntity = new InsuranceEntity();
        insuranceEntity.setPolicyHolderFirstName("Dummy");
        insuranceEntity.setPrimaryRxBinString("Rxbin");
        insuranceEntity.setPolicyHolderLastNameString("DummyLastName");
    }

    private void createPrescriberEntity(){
        prescriberEntity = new PrescriberEntity();
        prescriberEntity.setNpiString("npi");
        prescriberEntity.setPrescriberFirstName("ram");
        prescriberEntity.setPrescriberLastName("Gupta");
        prescriberEntity.setPrescriberTitle("Title");
    }

    private void createPrescriptionsEntityList(){
        PrescriptionsEntity prescriptionsEntity = new PrescriptionsEntity();
        prescriptionsEntity.setNdc("ndc123");
        prescriptionsEntity.setSupply("supply");
        prescriptionsEntity.setStrength("20");
        prescriptionsEntity.setRefills("100");
        prescriptionsEntity.setStrengthUnit("unit");
        prescriptionsEntity.setRxNumber("rx123");
        prescriptionsEntity.setDrugName("Aspirine");
        prescriptionsEntityArrayList = new ArrayList<>();
        prescriptionsEntityArrayList.add(prescriptionsEntity);
    }

    private void createPatientEntity(){
        patientEntity = new PatientEntity();
        patientEntity.setId(1);
        patientEntity.setFirstName("Khemraj");
        patientEntity.setLastName("Bawaskar");
        patientEntity.setGender("Male");
        patientEntity.setBirth_date("1999-11-11");
        patientEntity.setAddressesEntities(addressesEntityArrayList);
        patientEntity.setClinicalEntity(clinicalEntity);
        patientEntity.setContactEntities(contactEntityArrayList);
        patientEntity.setInsuranceEntity(insuranceEntity);
        patientEntity.setPrescriberEntity(prescriberEntity);
        patientEntity.setPrescriptionsEntities(prescriptionsEntityArrayList);

    }

    private void createPatientModel(){
        patientModel = new PatientModel(patientEntity);
    }

    @BeforeEach
    void setup() {
        createAddressEntityList();
        createAddressEntityList();
        createClinicalEntity();
        createInsuranceEntity();
        createContactEntityList();
        createPrescriberEntity();
        createPrescriptionsEntityList();
        createPatientEntity();
        createPatientModel();
    }

    @DisplayName("Testing savePatient() method of Controller layer to create new patient entry")
    @Test
    void savePatientTest() throws Exception {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModel);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENT_CREATED);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.savePatient(any(PatientModel.class))).thenReturn(statusResponse);

        this.mockMvc.perform(post("/api/patient/addpatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseObject.firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.responseObject.lastName", is(patientModel.getLastName())))
                .andExpect(jsonPath("$.responseObject.gender", is(patientModel.getGender())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing ConstraintViolation Exception for savePatient() method of Controller layer to create new patient entry with empty first name and last name")
    @Test
    void savePatientFirstLastNameEmptyTest() throws Exception {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        Set<ConstraintViolation<String>> exceptionMessagesSet = new HashSet<>();
        exceptionMessagesSet.add(new ConstraintViolation<String>() {
            @Override
            public String getMessage() {
                return FIRST_NAME_EMPTY;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public String getRootBean() {
                return null;
            }

            @Override
            public Class<String> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> type) {
                return null;
            }
        });

        statusResponse.setResponseObject(null);
        statusResponse.setStatus(ERROR);
        msgResponseList.add(FIRST_NAME_EMPTY);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.savePatient(any(PatientModel.class))).thenThrow(new ConstraintViolationException(exceptionMessagesSet));

        this.mockMvc.perform(post("/api/patient/addpatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ERROR)))
                .andExpect(jsonPath("$.responseMessage", is(msgResponseList)));
    }

    @DisplayName("Testing exception handling in savePatient() method of Controller layer for internal server error")
    @Test
    void savePatientExceptionTest() throws Exception {
        when(patientService.savePatient(any(PatientModel.class))).thenThrow(ResponseStatusException.class);

        this.mockMvc.perform(post("/api/patient/addpatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing getAllPatients() method of Controller layer to fetch all patient entries")
    @Test
    void getAllPatientsTest() throws Exception {
        List<PatientModel> allPatientModelList = new ArrayList<>();
        allPatientModelList.add(patientModel);
        allPatientModelList.add(patientModel);
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(allPatientModelList);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(ALL_PATIENTS_DATA_FOUND);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.getAllPatients()).thenReturn(statusResponse);

        this.mockMvc.perform(get("/api/patient/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.size()", is(allPatientModelList.size())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing exception handling in getAllPatients() method of Controller layer for internal server error")
    @Test
    void getAllPatientsExceptionTest() throws Exception {
        when(patientService.getAllPatients()).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(get("/api/patient/"))
                        .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing getPatientById() method of Controller layer to fetch patient entries for given id")
    @Test
    void getPatientByIdTest() throws Exception {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModel);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENTS_FOUND);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.getPatientById(anyString())).thenReturn(statusResponse);

        this.mockMvc.perform(get("/api/patient/filterid?patientId=1", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.responseObject.lastName", is(patientModel.getLastName())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing exception handling in getPatientById() method of Controller layer to fetch patient entries for given id")
    @Test
    void getPatientByIdExceptionTest() throws Exception {
        when(patientService.getPatientById(anyString())).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(get("/api/patient/filterid?patientId=1", 1))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing getPatientByFirstName() method of Controller layer to fetch patient entries for given firstname")
    @Test
    void getPatientByFirstNameTest() throws Exception {
        List<PatientModel> patientModelList = new ArrayList<>();
        patientModelList.add(patientModel);
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModelList);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENTS_FOUND_BY_FIRSTNAME + patientModel.getFirstName());
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.getPatientByName(anyString())).thenReturn(statusResponse);

        this.mockMvc.perform(get("/api/patient/filterfirstname?firstName=Khemraj", "Khemraj"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.size()", is(1)))
                .andExpect(jsonPath("$.responseObject[0].firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));

    }

    @DisplayName("Testing exception handling in getPatientByFirstName() method of Controller layer to fetch patient entries for given firstname")
    @Test
    void getPatientByFirstNameExceptionTest() throws Exception {
        when(patientService.getPatientByName(anyString())).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(get("/api/patient/filterfirstname?firstName=Khemraj", "Khemraj"))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing deletePatient() method of Controller layer to delete patient entries for given id")
    @Test
    void deletePatientTest() throws Exception {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModel);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENT_DELETED);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.deletePatient(anyString())).thenReturn(statusResponse);

        this.mockMvc.perform(delete("/api/patient/deletepatient?patientId=1", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.responseObject.lastName", is(patientModel.getLastName())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing exception handling in deletePatient() method of Controller layer to delete patient entries for given id")
    @Test
    void deletePatientExceptionTest() throws Exception {
        when(patientService.deletePatient(anyString())).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(delete("/api/patient/deletepatient?patientId=1", 1L))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing updatePatient() method of Controller layer to update existing patient entries.")
    @Test
    void updatePatientTest() throws Exception {
        patientModel.setFirstName("Kushagra");
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModel);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENT_UPDATED);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.updatePatient(any(PatientModel.class))).thenReturn(statusResponse);

        this.mockMvc.perform(put("/api/patient/updatepatient/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.responseObject.lastName", is(patientModel.getLastName())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing ConstraintViolation Exception for updatePatient() method of Controller layer to update existing patient entry with empty first name and last name")
    @Test
    void updatePatientFirstLastNameEmptyTest() throws Exception {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        Set<ConstraintViolation<String>> exceptionMessagesSet = new HashSet<>();
        exceptionMessagesSet.add(new ConstraintViolation<String>() {
            @Override
            public String getMessage() {
                return FIRST_NAME_EMPTY;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public String getRootBean() {
                return null;
            }

            @Override
            public Class<String> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> type) {
                return null;
            }
        });

        statusResponse.setResponseObject(null);
        statusResponse.setStatus(ERROR);
        msgResponseList.add(FIRST_NAME_EMPTY);
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.updatePatient(any(PatientModel.class))).thenThrow(new ConstraintViolationException(exceptionMessagesSet));

        this.mockMvc.perform(put("/api/patient/updatepatient/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(ERROR)))
                .andExpect(jsonPath("$.responseMessage", is(msgResponseList)));
    }

    @DisplayName("Testing exception handling in updatePatient() method of Controller layer to update existing patient entries.")
    @Test
    void updatePatientExceptionTest() throws Exception {
        when(patientService.updatePatient(any(PatientModel.class))).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(put("/api/patient/updatepatient/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientModel)))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Testing getPatientByFirstLastName() method of Controller layer to fetch patient entries for given firstname and lastname")
    @Test
    void getPatientByFirstLastNameTest() throws Exception {
        List<PatientModel> patientModelList = new ArrayList<>();
        patientModelList.add(patientModel);
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgResponseList = new ArrayList<>();
        statusResponse.setResponseObject(patientModelList);
        statusResponse.setStatus(SUCCESS);
        msgResponseList.add(PATIENTS_FOUND_BY_FIRSTNAME_LASTNAME + patientModel.getFirstName() + "&" + patientModel.getLastName());
        statusResponse.setResponseMessage(msgResponseList);

        when(patientService.getPatientByNameAndLastName(anyString(), anyString())).thenReturn(statusResponse);

        this.mockMvc.perform(get("/api/patient/filterfirstnameandlastname?firstName=Khemraj&lastName=Bawaskar", "Khemraj", "Bawaskar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseObject.size()", is(1)))
                .andExpect(jsonPath("$.responseObject[0].firstName", is(patientModel.getFirstName())))
                .andExpect(jsonPath("$.responseObject[0].lastName", is(patientModel.getLastName())))
                .andExpect(jsonPath("$.status", is(SUCCESS)));
    }

    @DisplayName("Testing exception handling in getPatientByFirstLastName() method of Controller layer to fetch patient entries for given firstname and lastname")
    @Test
    void getPatientByFirstLastNameExceptionTest() throws Exception {
        when(patientService.getPatientByNameAndLastName(anyString(), anyString())).thenThrow(ResponseStatusException.class);
        this.mockMvc.perform(get("/api/patient/filterfirstnameandlastname?firstName=Khemraj&lastName=Bawaskar", "Khemraj", "Bawaskar"))
                .andExpect(status().isInternalServerError());
    }
}
