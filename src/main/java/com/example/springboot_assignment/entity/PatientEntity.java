package com.example.springboot_assignment.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import com.example.springboot_assignment.model.PatientModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.example.springboot_assignment.constants.LogConstants.*;


@Data
@NoArgsConstructor
@Entity
@Table(name="patients", uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName"}))
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "firstName", nullable = false)
	@NotEmpty(message = FIRST_NAME_EMPTY)
	private String firstName;
	
	@Column(name = "lastName", nullable = false)
	@NotEmpty(message = LAST_NAME_EMPTY)
	private String lastName;

	@Column(name = "gender")
	private String gender;

	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_clinical_id", referencedColumnName = "id")
	@Valid
	private ClinicalEntity clinicalEntity;

	@Column(name = "birth_date", nullable = false)
	private String birth_date;

	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_insurance_id", referencedColumnName = "id")
	@Valid
	private InsuranceEntity insuranceEntity;

	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_prescriber_id", referencedColumnName = "id")
	private PrescriberEntity prescriberEntity;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_contact_id", referencedColumnName = "id")
	private List<ContactEntity> contactEntities;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_address_id", referencedColumnName = "id")
	private List<AddressesEntity> addressesEntities;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "p_prescription_id", referencedColumnName = "id")
	private List<PrescriptionsEntity> prescriptionsEntities ;


	public PatientEntity(long id, String firstName, String lastName, String birth_date, String gender, ClinicalEntity clinicalEntity, InsuranceEntity insuranceEntity, PrescriberEntity prescriberEntity, ArrayList<ContactEntity> contactEntities, ArrayList<AddressesEntity> addresses, ArrayList<PrescriptionsEntity> prescriptions) {
		super();
		//this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth_date = birth_date;
		this.gender = gender;
		this.clinicalEntity = clinicalEntity;
		this.insuranceEntity = insuranceEntity;
		this.contactEntities = contactEntities;
		this.prescriberEntity = prescriberEntity;
		this.addressesEntities = addresses;
		this.prescriptionsEntities = prescriptions;
	}


	public PatientEntity(String firstName, String lastName, String birth_date, String gender, ClinicalEntity clinicalEntity, InsuranceEntity insuranceEntity, PrescriberEntity prescriberEntity, ArrayList<ContactEntity> contactEntities, ArrayList<AddressesEntity> addresses, ArrayList<PrescriptionsEntity> prescriptions) {
		super();
		//this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth_date = birth_date;
		this.gender = gender;
		this.clinicalEntity = clinicalEntity;
		this.insuranceEntity = insuranceEntity;
		this.contactEntities = contactEntities;
		this.prescriberEntity = prescriberEntity;
		this.addressesEntities = addresses;
		this.prescriptionsEntities = prescriptions;
	}

	public PatientEntity(PatientModel patientModel){

		this.id = patientModel.getId();
		this.firstName = patientModel.getFirstName();
		this.lastName = patientModel.getLastName();
		this.birth_date = patientModel.getBirth_date();
		this.gender = patientModel.getGender();
		this.clinicalEntity = new ClinicalEntity(patientModel.getClinicalModel());
		this.insuranceEntity = new InsuranceEntity(patientModel.getInsuranceModel());
		this.contactEntities = patientModel.getContactModels().stream()
				.map(ContactEntity::new).collect(Collectors.toList());
		this.prescriberEntity = new PrescriberEntity(patientModel.getPrescriberModel());
		this.addressesEntities = patientModel.getAddressesModels().stream()
				.map(AddressesEntity::new).collect(Collectors.toList());
		this.prescriptionsEntities = patientModel.getPrescriptionsModels().stream()
				.map(PrescriptionsEntity::new).collect(Collectors.toList());

	}

	public List<PrescriptionsEntity> getPrescriptions() {
		return prescriptionsEntities;
	}

	public void setPrescriptions(List<PrescriptionsEntity> prescriptions) {
		this.prescriptionsEntities = prescriptions;
	}

	public List<AddressesEntity> getAddresses() {
		return addressesEntities;
	}

	public void setAddresses(List<AddressesEntity> addresses) {
		this.addressesEntities = addresses;
	}

	public List<ContactEntity> getContactEntities() {
		return contactEntities;
	}

	public void setContactEntities(List<ContactEntity> contactEntities) {
		this.contactEntities = contactEntities;
	}

	public PrescriberEntity getPrescriberEntity() {
		return prescriberEntity;
	}

	public void setPrescriberEntity(PrescriberEntity prescriberEntity) {
		this.prescriberEntity = prescriberEntity;
	}

	public InsuranceEntity getInsuranceEntity() {
		return insuranceEntity;
	}

	public void setInsuranceEntity(InsuranceEntity insuranceEntity) {
		this.insuranceEntity = insuranceEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ClinicalEntity getClinicalEntity() {
		return clinicalEntity;
	}

	public void setClinicalEntity(ClinicalEntity clinicalEntity) {
		this.clinicalEntity = clinicalEntity;
	}
	
}
