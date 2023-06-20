package com.example.springboot_assignment.entity;

import com.example.springboot_assignment.model.AddressesModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Addresses")
public class AddressesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "addressType")
	private String addressType;
	
	@Column(name = "line")
	private String line;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pinCode")
	private String pinCode;

	public AddressesEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressesEntity(Long id, String addressType, String line, String city, String pinCode) {
		super();
		//this.id = id;
		this.addressType = addressType;
		this.line = line;
		//this.line2 = line2;
		this.city = city;
		this.pinCode = pinCode;
		//this.stateCode = stateCode;
	}

	public AddressesEntity(String addressType, String line, String city, String pinCode) {
		super();
		this.addressType = addressType;
		this.line = line;
		this.city = city;
		this.pinCode = pinCode;
	}

	public AddressesEntity(AddressesModel addressesModel){
		this.id = addressesModel.getId();
		this.addressType = addressesModel.getAddressType();
		this.line = addressesModel.getLine();
		this.city = addressesModel.getCity();
		this.pinCode = addressesModel.getPinCode();
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pincode) {
		this.pinCode = pincode;
	}
}
