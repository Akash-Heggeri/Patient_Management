package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.PrescriptionsEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionsModel {
    private long id;
    private String ndc;
    private String rxNumber;
    private String drugName;
    private String strength;
    private String strengthUnit;
    private String supply;
    private String refills;

    public PrescriptionsModel(PrescriptionsEntity prescriptionsEntity){
        this.id = prescriptionsEntity.getId();
        this.ndc = prescriptionsEntity.getNdc();
        this.rxNumber = prescriptionsEntity.getRxNumber();
        this.drugName = prescriptionsEntity.getDrugName();
        this.strength = prescriptionsEntity.getStrength();
        this.strengthUnit = prescriptionsEntity.getStrengthUnit();
        this.supply = prescriptionsEntity.getSupply();
        this.refills = prescriptionsEntity.getRefills();
    }
}
