package com.example.springboot_assignment.entity;

import com.example.springboot_assignment.model.AllergiesModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Allergies")
public class AllergiesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "clinicalStatus")
	private String clinicalStatus;
	
	@Column(name = "severityString")
	private String severityString;

	public AllergiesEntity(Long id, String category, String clinicalStatus, String severityString) {
		super();
		//this.id = id;
		this.category = category;
		this.clinicalStatus = clinicalStatus;
		this.severityString = severityString;
	}

	public AllergiesEntity(String category, String clinicalStatus, String severityString) {
		super();
		this.category = category;
		this.clinicalStatus = clinicalStatus;
		this.severityString = severityString;
	}

	public AllergiesEntity(AllergiesModel allergiesModel){
		this.id = allergiesModel.getId();
		this.severityString = allergiesModel.getSeverityString();
		this.category = allergiesModel.getCategory();
		this.clinicalStatus = allergiesModel.getClinicalStatus();
	}

	public AllergiesEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public String getClinicalStatus() {
		return clinicalStatus;
	}

	public void setClinicalStatus(String clinicalStatus) {
		this.clinicalStatus = clinicalStatus;
	}
	public String getSeverityString() {
		return severityString;
	}

	public void setSeverityString(String severityString) {
		this.severityString = severityString;
	}
}
