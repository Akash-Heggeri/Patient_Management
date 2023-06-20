package com.example.springboot_assignment.entity;

import com.example.springboot_assignment.model.PrescriptionsModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Prescriptions")
public class PrescriptionsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "ndc")
	private String ndc;
	
	@Column(name = "rxNumber")
	private String rxNumber;
	
	@Column(name = "drugName")
	private String drugName;
	
	@Column(name = "strength")
	private String strength;
	
	@Column(name = "strengthUnit")
	private String strengthUnit;
	
	@Column(name = "supply")
	private String supply;
	
	@Column(name = "refills")
	private String refills;

	public PrescriptionsEntity(Long id, String ndc, String rxNumber, String drugName, String strength, String strengthUnit,
							   String supply, String refills) {
		super();
		//this.id = id;
		this.ndc = ndc;
		this.rxNumber = rxNumber;
		this.drugName = drugName;
		this.strength = strength;
		this.strengthUnit = strengthUnit;
		this.supply = supply;
		this.refills = refills;
	}

	public PrescriptionsEntity(String ndc, String rxNumber, String drugName, String strength, String strengthUnit,
							   String supply, String refills) {
		super();
		this.ndc = ndc;
		this.rxNumber = rxNumber;
		this.drugName = drugName;
		this.strength = strength;
		this.strengthUnit = strengthUnit;
		this.supply = supply;
		this.refills = refills;
	}

	public PrescriptionsEntity(PrescriptionsModel prescriptionsModel){
		this.id = prescriptionsModel.getId();
		this.ndc = prescriptionsModel.getNdc();
		this.rxNumber = prescriptionsModel.getRxNumber();
		this.drugName = prescriptionsModel.getDrugName();
		this.strength = prescriptionsModel.getStrength();
		this.strengthUnit = prescriptionsModel.getStrengthUnit();
		this.supply = prescriptionsModel.getSupply();
		this.refills = prescriptionsModel.getRefills();
	}

	public PrescriptionsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNdc() {
		return ndc;
	}
	public void setNdc(String ndc) {
		this.ndc = ndc;
	}
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getStrengthUnit() {
		return strengthUnit;
	}
	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}
	public String getSupply() {
		return supply;
	}
	public void setSupply(String supply) {
		this.supply = supply;
	}
	public String getRefills() {
		return refills;
	}
	public void setRefills(String refills) {
		this.refills = refills;
	}
}
