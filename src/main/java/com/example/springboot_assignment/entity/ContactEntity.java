package com.example.springboot_assignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import com.example.springboot_assignment.utility.CustomAnnotation.EmailOrPhone;
import com.example.springboot_assignment.model.ContactModel;


@Entity
@Table(name = "Contact")
public class ContactEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "contactType", nullable = false)
	@NotEmpty(message = "Please select contact type")
	private String contactType;
	
	@Column(name = "value", nullable = false)
	@NotEmpty(message = "Contact can't be empty")
	@EmailOrPhone
	private String value;

	public ContactEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactEntity(Long id, String contactType, String value) {
		super();
		this.contactType = contactType;
		this.value = value;
	}

	public ContactEntity(String contactType, String value) {
		super();
		this.contactType = contactType;
		this.value = value;
	}

	public ContactEntity(ContactModel contactModel){
		this.id = contactModel.getId();
		this.contactType = contactModel.getContactType();
		this.value = contactModel.getValue();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
