package com.example.springboot_assignment.entity;

import com.example.springboot_assignment.model.PrescriberModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Prescriber")
public class PrescriberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "npiString")
	private String npiString;
	
	@Column(name = "prescriberFirstName")
	private String prescriberFirstName;

	@Column(name = "prescriberLastName")
	private String prescriberLastName;
	
	@Column(name = "prescriberTitle")
	private String prescriberTitle;
	
	
	public PrescriberEntity(Long id, String npiString, String prescriberFirstName, String prescriberLastName, String prescriberTitle) {
		super();
		//this.id = id;
		this.npiString = npiString;
		this.prescriberFirstName = prescriberFirstName;
		this.prescriberLastName = prescriberLastName;
		this.prescriberTitle = prescriberTitle;
	}

	public PrescriberEntity(String npiString, String prescriberFirstName, String prescriberLastName, String prescriberTitle) {
		super();
		//this.id = id;
		this.npiString = npiString;
		this.prescriberFirstName = prescriberFirstName;
		this.prescriberLastName = prescriberLastName;
		this.prescriberTitle = prescriberTitle;
	}

	public PrescriberEntity(PrescriberModel prescriberModel){
		this.id = prescriberModel.getId();
		this.npiString = prescriberModel.getNpiString();
		this.prescriberTitle = prescriberModel.getPrescriberTitle();
		this.prescriberFirstName = prescriberModel.getPrescriberFirstName();
		this.prescriberLastName = prescriberModel.getPrescriberLastName();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PrescriberEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNpiString() {
		return npiString;
	}

	public void setNpiString(String npiString) {
		this.npiString = npiString;
	}
	public String getPrescriberFirstName() {
		return prescriberFirstName;
	}

	public void setPrescriberFirstName(String prescriberFirstName) {
		this.prescriberFirstName = prescriberFirstName;
	}
	public String getPrescriberLastName() {
		return prescriberLastName;
	}

	public void setPrescriberLastName(String prescriberLastName) {
		this.prescriberLastName = prescriberLastName;
	}
	public String getPrescriberTitle() {
		return prescriberTitle;
	}

	public void setPrescriberTitle(String prescriberTitle) {
		this.prescriberTitle = prescriberTitle;
	}
	
	
}
