package com.example.springboot_assignment.entity;

import com.example.springboot_assignment.model.InsuranceModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Insurance")
public class InsuranceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "policyHolderFirstName")
	private String policyHolderFirstName;

	@Column(name = "policyHolderLastNameString")
	private String policyHolderLastNameString;

	@Column(name = "primaryRxBinString")
	private String primaryRxBinString;
	
	public InsuranceEntity(Long id, String policyHolderFirstName, String policyHolderLastNameString, String primaryRxBinString) {
		super();
		//this.id = id;
		this.policyHolderFirstName = policyHolderFirstName;
		this.policyHolderLastNameString = policyHolderLastNameString;
		this.primaryRxBinString = primaryRxBinString;
	}

	public InsuranceEntity(String policyHolderFirstName, String policyHolderLastNameString, String primaryRxBinString) {
		super();
		//this.id = id;
		this.policyHolderFirstName = policyHolderFirstName;
		this.policyHolderLastNameString = policyHolderLastNameString;
		this.primaryRxBinString = primaryRxBinString;
	}

	public InsuranceEntity(InsuranceModel insuranceModel){
		this.id=insuranceModel.getId();
		this.policyHolderFirstName = insuranceModel.getPolicyHolderFirstName();
		this.policyHolderLastNameString = insuranceModel.getPolicyHolderLastNameString();
		this.primaryRxBinString =  insuranceModel.getPrimaryRxBinString();
	}
	
	public InsuranceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getPolicyHolderFirstName() {
		return policyHolderFirstName;
	}

	public void setPolicyHolderFirstName(String policyHolderFirstName) {
		this.policyHolderFirstName = policyHolderFirstName;
	}
	public String getPolicyHolderLastNameString() {
		return policyHolderLastNameString;
	}
	
	public void setPolicyHolderLastNameString(String policyHolderLastNameString) {
		this.policyHolderLastNameString = policyHolderLastNameString;
	}
	public String getPrimaryRxBinString() {
		return primaryRxBinString;
	}
	
	public void setPrimaryRxBinString(String primaryRxBinString) {
		this.primaryRxBinString = primaryRxBinString;
	}
	
	
}
