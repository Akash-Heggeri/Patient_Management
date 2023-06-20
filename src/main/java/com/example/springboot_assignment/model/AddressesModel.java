package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.AddressesEntity;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class AddressesModel {
    private long id;
    private String addressType;
    private String line;
    private String city;
    private String pinCode;

    public AddressesModel(AddressesEntity addressesEntity){
        this.id = addressesEntity.getId();
        this.addressType = addressesEntity.getAddressType();
        this.line = addressesEntity.getLine();
        this.city = addressesEntity.getCity();
        this.pinCode = addressesEntity.getPinCode();
    }
}
