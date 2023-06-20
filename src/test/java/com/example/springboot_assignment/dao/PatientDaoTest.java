package com.example.springboot_assignment.dao;

import com.example.springboot_assignment.dao.impl.PatientDaoImpl;
import com.example.springboot_assignment.entity.*;
import com.example.springboot_assignment.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientDaoTest {
    @Mock
    private PatientRepository patientRepository ;
    @InjectMocks
    private PatientDaoImpl patientDao;
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

    @DisplayName("Testing savePatient() method of DAO layer to create new patient entry")
    @Test
    void savePatientTest() {
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);
        PatientEntity createdPatientEntity = patientDao.savePatient(patientEntity);
        assertNotNull(createdPatientEntity);
        assertThat(createdPatientEntity.getId()).isNotEqualTo(null);
    }

    @DisplayName("Testing getAllPatients() method of DAO layer to fetch all patient entries")
    @Test
    void getAllPatientsTest() {
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        patientEntityList.add(patientEntity);
        when(patientRepository.findAll()).thenReturn(patientEntityList);

        List<PatientEntity> outputPatientEntityList = patientDao.getAllPatients();
        assertEquals(2, outputPatientEntityList.size());
        assertNotNull(outputPatientEntityList);
    }

    @DisplayName("Testing getPatientById() method of DAO layer to fetch patient entries for given id")
    @Test
    void getPatientByIdTest() {
        patientEntity.setId(0L);
        when(patientRepository.findById(0L)).thenReturn(Optional.ofNullable(patientEntity));

        Optional<PatientEntity> existingOptionalPatientEntity = patientDao.getPatientById(patientEntity.getId());
        assertNotNull(existingOptionalPatientEntity);
        assertThat(existingOptionalPatientEntity.get().getId()).isNotEqualTo(null);
        assertThat(existingOptionalPatientEntity.get().getId()).isEqualTo(patientEntity.getId());
    }

    @DisplayName("Testing getPatientByFirstName() method of DAO layer to fetch patient entries for given firstname")
    @Test
    void getPatientByFirstNameTest() {
        patientEntity.setFirstName("Khemraj");
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        patientEntityList.add(patientEntity);
        when(patientRepository.findByFirstName("Khemraj")).thenReturn(patientEntityList);
        List<PatientEntity> outputPatientEntityList = patientDao.getPatientByFirstname("Khemraj");

        assertNotNull(outputPatientEntityList);
        assertThat(outputPatientEntityList.size()).isEqualTo(2);
        assertThat(outputPatientEntityList.get(0).getFirstName()).isEqualTo("Khemraj");
    }

    @DisplayName("Testing getPatientByFirstLastName() method of DAO layer to fetch patient entries for given firstname and lastname")
    @Test
    void getPatientByFirstnameAndLastName() {
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList.add(patientEntity);
        when(patientRepository.findByFirstNameAndLastName("Khemraj", "Bawaskar")).thenReturn(patientEntityList);
        List<PatientEntity> outputPatientEntityList = patientDao.getPatientByFirstnameAndLastName("Khemraj","Bawaskar");

        assertNotNull(outputPatientEntityList);
        assertThat(outputPatientEntityList.size()).isEqualTo(1);
        assertThat(outputPatientEntityList.get(0).getFirstName()).isEqualTo("Khemraj");
        assertThat(outputPatientEntityList.get(0).getLastName()).isEqualTo("Bawaskar");
    }

    @DisplayName("Testing deletePatient() method of DAO layer to delete patient entries for given id")
    @Test
    void deletePatientTest() {
        patientEntity.setId(0L);
        doNothing().when(patientRepository).deleteById(anyLong());
        when(patientRepository.findById(0L)).thenReturn(Optional.ofNullable(patientEntity));
        PatientEntity deletedPatientEntity = patientDao.deletePatient(patientEntity.getId());

        verify(patientRepository, times(1)).deleteById(anyLong());
        assertNotNull(deletedPatientEntity);
        assertThat(deletedPatientEntity.getId()).isNotEqualTo(null);
        assertThat(deletedPatientEntity.getId()).isEqualTo(patientEntity.getId());
    }
}
