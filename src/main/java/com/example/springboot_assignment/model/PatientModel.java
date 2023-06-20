package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springboot_assignment.constants.LogConstants.FIRST_NAME_EMPTY;
import static com.example.springboot_assignment.constants.LogConstants.LAST_NAME_EMPTY;

@Data
@NoArgsConstructor

public class PatientModel {

    private long id;
    @NotEmpty(message = FIRST_NAME_EMPTY)
    private String firstName;
    @NotEmpty(message = LAST_NAME_EMPTY)
    private String lastName;
    private String birth_date;
    private String gender;
    private ClinicalModel clinicalModel;
    private InsuranceModel insuranceModel;
    private PrescriberModel prescriberModel;
    private List<ContactModel> contactModels;
    private List<AddressesModel> addressesModels;
    private List<PrescriptionsModel> prescriptionsModels ;

    public PatientModel(PatientEntity patientEntity){

        this.id = patientEntity.getId();
        this.firstName = patientEntity.getFirstName();
        this.lastName = patientEntity.getLastName();
        this.birth_date = patientEntity.getBirth_date();
        this.gender = patientEntity.getGender();
        this.clinicalModel = new ClinicalModel(patientEntity.getClinicalEntity());
        this.insuranceModel = new InsuranceModel(patientEntity.getInsuranceEntity());
        this.prescriberModel = new PrescriberModel(patientEntity.getPrescriberEntity());

        this.contactModels = patientEntity.getContactEntities().stream()
                .map(ContactModel::new).collect(Collectors.toList());

        this.addressesModels = patientEntity.getAddressesEntities().stream()
                .map(AddressesModel::new).collect(Collectors.toList());

        this.prescriptionsModels = patientEntity.getPrescriptionsEntities().stream()
                .map(PrescriptionsModel::new).collect(Collectors.toList());
    }

}
