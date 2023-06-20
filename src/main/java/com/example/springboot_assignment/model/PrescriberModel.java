package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.PrescriberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriberModel {

    private long id;
    private String npiString;
    private String prescriberFirstName;
    private String prescriberLastName;
    private String prescriberTitle;

    public PrescriberModel(PrescriberEntity prescriberEntity){
        this.id = prescriberEntity.getId();
        this.npiString = prescriberEntity.getNpiString();
        this.prescriberTitle = prescriberEntity.getPrescriberTitle();
        this.prescriberFirstName = prescriberEntity.getPrescriberFirstName();
        this.prescriberLastName = prescriberEntity.getPrescriberLastName();

    }
}
