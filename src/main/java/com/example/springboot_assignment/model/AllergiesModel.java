package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.AllergiesEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllergiesModel {
    private long id;
    private String category;
    private String clinicalStatus;
    private String severityString;

    public AllergiesModel(AllergiesEntity allergiesEntity){
        this.id = allergiesEntity.getId();
        this.severityString = allergiesEntity.getSeverityString();
        this.category = allergiesEntity.getCategory();
        this.clinicalStatus = allergiesEntity.getClinicalStatus();
    }

}
