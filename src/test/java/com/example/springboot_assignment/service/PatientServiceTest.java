package com.example.springboot_assignment.service;
import com.example.springboot_assignment.dao.PatientDao;
import com.example.springboot_assignment.utility.StatusResponse;
import com.example.springboot_assignment.entity.*;
import com.example.springboot_assignment.model.PatientModel;
import com.example.springboot_assignment.repository.PatientRepository;
import com.example.springboot_assignment.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import static com.example.springboot_assignment.constants.LogConstants.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientDao patientDao;
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientServiceImpl patientService;
    private PatientEntity patientEntity;
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
    }

    @DisplayName("Testing savePatient() method of Service layer to create new patient entry")
    @Test
    void savePatientTest() {
        StatusResponse statusResponse = new StatusResponse();
        when(patientDao.savePatient(any(PatientEntity.class))).thenReturn(patientEntity);
        statusResponse = patientService.savePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(new PatientModel(patientEntity));
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing exception handling for already existing patient in savePatient() method of Service layer to create new patient entry")
    @Test
    void savePatientExceptionTest() {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgList = new ArrayList<>();
        msgList.add(PATIENT_ALREADY_EXIST + patientEntity.getFirstName() + " " + patientEntity.getLastName());
        when(patientDao.savePatient(any(PatientEntity.class))).thenThrow(DataIntegrityViolationException.class);
        statusResponse = patientService.savePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing exception handling for already existing patient in updatePatient() method of Service layer to create new patient entry")
    @Test
    void updatePatientExceptionTest() {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgList = new ArrayList<>();
        msgList.add(PATIENT_ALREADY_EXIST + patientEntity.getFirstName() + " " + patientEntity.getLastName());
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.ofNullable(patientEntity));
        when(patientDao.savePatient(any(PatientEntity.class))).thenThrow(DataIntegrityViolationException.class);
        statusResponse = patientService.updatePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing getAllPatients() method of Service layer to fetch all patient entries")
    @Test
    void getAllPatientsTest() {
        StatusResponse statusResponse = new StatusResponse();
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        patientEntityList.add(patientEntity);
        List<PatientModel> patientModelList;

        patientModelList = patientEntityList.stream()
                .map(PatientModel::new).collect(Collectors.toList());

        when(patientDao.getAllPatients()).thenReturn(patientEntityList);
        statusResponse = patientService.getAllPatients();

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(patientModelList);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing for handling the empty list in getAllPatients() method of Service layer to fetch all patient entries")
    @Test
    void getAllPatientsEmptyListTest(){
        StatusResponse statusResponse = new StatusResponse();
        List<PatientEntity> patientEntityList = new ArrayList<>();
        when(patientDao.getAllPatients()).thenReturn(patientEntityList);
        statusResponse = patientService.getAllPatients();

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
    }

    @DisplayName("Testing getPatientById() method of Service layer to fetch patient entries for given id")
    @Test
    void getPatientByIdTest() {
        StatusResponse statusResponse;
        PatientModel fetchedPatientModel = new PatientModel(patientEntity);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.ofNullable(patientEntity));
        statusResponse = patientService.getPatientById("0");

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(fetchedPatientModel);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing invalid Id exception in getPatientById() method of Service layer to fetch patient entries for given id")
    @Test
    void getPatientByIdExceptionTest() {
        StatusResponse statusResponse;
        String id = "1";
        List<String> msgList = new ArrayList<>();
        msgList.add(INVALID_PATIENT_ID + id);
        when(patientDao.getPatientById(1L)).thenThrow(NumberFormatException.class);
        statusResponse = patientService.getPatientById(id);

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }


    @DisplayName("Testing invalid Id exception in deletePatient() method of Service layer to delete patient entries for given id")
    @Test
    void deletePatientExceptionTest() {
        StatusResponse statusResponse;
        String id = "1";
        List<String> msgList = new ArrayList<>();
        msgList.add(INVALID_PATIENT_ID + id);
        when(patientDao.getPatientById(1L)).thenThrow(NumberFormatException.class);
        statusResponse = patientService.deletePatient(id);

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }
    @DisplayName("Testing getPatientByName() method of Service layer to fetch patient entries for given firstname")
    @Test
    void getPatientByNameTest() {
        StatusResponse statusResponse;
        patientEntity.setFirstName("Khemraj");
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        patientEntityList.add(patientEntity);
        List<PatientModel> patientModelList;

        patientModelList = patientEntityList.stream()
                .map(PatientModel::new).collect(Collectors.toList());

        when(patientDao.getPatientByFirstname(anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByName("Khemraj");

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(patientModelList);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing handling of getPatientByFirstName() method of Service layer to fetch patient entries for given empty string as firstname")
    @Test
    void getPatientByNameEmptyTest() {
        StatusResponse statusResponse;
        patientEntity.setFirstName("Khemraj");
        String emptyFirstNameString = "";
        List<String> msgList = new ArrayList<>();
        msgList.add(FIRST_NAME_EMPTY);
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        patientEntityList.add(patientEntity);
        lenient().when(patientDao.getPatientByFirstname(anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByName(emptyFirstNameString);

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing getPatientByNameAndLastName() method of Service layer to fetch patient entries for given firstname and lastname")
    @Test
    void getPatientByNameAndLastNameTest() {
        StatusResponse statusResponse;
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        List<PatientModel> patientModelList;
        patientModelList = patientEntityList.stream()
                .map(PatientModel::new).collect(Collectors.toList());

        when(patientDao.getPatientByFirstnameAndLastName(anyString(),anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByNameAndLastName("Khemraj", "Bawaskar");

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(patientModelList);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing handling of getPatientByNameAndLastName() method of Service layer to fetch patient entries for given empty strings as firstname & lastname")
    @Test
    void getPatientByNameAndLastNameEmptyTest() {
        StatusResponse statusResponse;
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        List<String> msgList = new ArrayList<>();
        msgList.add(FIRST_NAME_EMPTY);
        msgList.add(LAST_NAME_EMPTY);
        lenient().when(patientDao.getPatientByFirstnameAndLastName(anyString(),anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByNameAndLastName("", "");

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
    }

    @DisplayName("Testing updatePatient() method of Service layer to update existing patient entries.")
    @Test
    void updatePatientTest() {
        StatusResponse statusResponse;
        patientEntity.setFirstName("Kushagra");
        PatientModel updatedPatientModel = new PatientModel(patientEntity);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.ofNullable(patientEntity));
        when(patientDao.savePatient(any(PatientEntity.class))).thenReturn(patientEntity);
        statusResponse = patientService.updatePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(updatedPatientModel);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);
    }

    @DisplayName("Testing deletePatient() method of Service layer to delete patient entries for given id")
    @Test
    void deletePatientTest() {
        StatusResponse statusResponse;
        PatientModel deletedPatientModel = new PatientModel(patientEntity);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.ofNullable(patientEntity));
        lenient().when(patientDao.deletePatient(anyLong())).thenReturn((patientEntity));
        statusResponse = patientService.deletePatient("0");

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(deletedPatientModel);
        assertThat(statusResponse.getStatus()).isEqualTo(SUCCESS);

    }

    @DisplayName("Testing condition handling if created patient is null in savePatient() method of Service layer")
    @Test
    void savePatientNullPatientTest() {
        StatusResponse statusResponse = new StatusResponse();
        List<String> msgList = new ArrayList<>();
        msgList.add(PATIENT_NOT_CREATED);
        when(patientDao.savePatient(any(PatientEntity.class))).thenReturn(null);
        statusResponse = patientService.savePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing getPatientById() method of Service layer if patient with given id doesn't exist")
    @Test
    void getPatientByIdPatientNotExistTest() {
        StatusResponse statusResponse;
        List<String> msgList = new ArrayList<>();
        String id = "0";
        msgList.add(PATIENT_NOT_FOUND + "id :" +id);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.empty());
        statusResponse = patientService.getPatientById(id);

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing updatePatient() method of Service layer if patient with given id doesn't exist")
    @Test
    void updatePatientNotExistTest() {
        StatusResponse statusResponse;
        List<String> msgList = new ArrayList<>();
        String id = "1";
        msgList.add(PATIENT_NOT_FOUND + "id :" + id);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.empty());
        statusResponse = patientService.updatePatient(new PatientModel(patientEntity));

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing deletePatient() method of Service layer if patient with given id doesn't exist")
    @Test
    void deletePatientNotExistTest() {
        StatusResponse statusResponse;
        List<String> msgList = new ArrayList<>();
        String id = "0";
        msgList.add(PATIENT_NOT_FOUND + "id :" + id);
        when(patientDao.getPatientById(anyLong())).thenReturn(Optional.empty());
        statusResponse = patientService.deletePatient(id);

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgList);
    }

    @DisplayName("Testing getPatientByNameAndLastName() method of Service layer if patient doesn't exist for given firstname and lastname")
    @Test
    void getPatientByNameAndLastNameNotExistTest() {
        StatusResponse statusResponse;
        List<PatientEntity> patientEntityList = new ArrayList<>();
        List<String> msgResponsesList = new ArrayList<>();
        msgResponsesList.add(PATIENT_NOT_FOUND + "first name: " + patientEntity.getFirstName() + " & lastname: " + patientEntity.getLastName());

        when(patientDao.getPatientByFirstnameAndLastName(anyString(),anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByNameAndLastName(patientEntity.getFirstName(), patientEntity.getLastName());

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgResponsesList);
    }

    @DisplayName("Testing getPatientByName() method of Service layer if patient doesn't exist for given firstname")
    @Test
    void getPatientByNameNotExistTest() {
        StatusResponse statusResponse;
        List<PatientEntity> patientEntityList = new ArrayList<>();
        List<String> msgResponsesList = new ArrayList<>();
        msgResponsesList.add(PATIENT_NOT_FOUND + "first name: " + patientEntity.getFirstName());

        when(patientDao.getPatientByFirstname(anyString())).thenReturn(patientEntityList);
        statusResponse = patientService.getPatientByName(patientEntity.getFirstName());

        assertNotNull(statusResponse);
        assertThat(statusResponse.getResponseObject()).isEqualTo(null);
        assertThat(statusResponse.getStatus()).isEqualTo(ERROR);
        assertThat(statusResponse.getResponseMessage()).isEqualTo(msgResponsesList);
    }
}
