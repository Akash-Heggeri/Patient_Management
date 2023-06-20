package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.InsuranceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsuranceModel {
    private long id;
    private String policyHolderFirstName;
    private String policyHolderLastNameString;
    private String primaryRxBinString;

    public InsuranceModel(InsuranceEntity insuranceEntity){
        this.id=insuranceEntity.getId();
        this.policyHolderFirstName = insuranceEntity.getPolicyHolderFirstName();
        this.policyHolderLastNameString = insuranceEntity.getPolicyHolderLastNameString();
        this.primaryRxBinString =  insuranceEntity.getPrimaryRxBinString();
    }
}
