package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.ClinicalEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ClinicalModel {

    private long id;
    private int height;
    private int weight;
    private List<AllergiesModel> allergies;

    public ClinicalModel(ClinicalEntity clinicalEntity){
        this.id = clinicalEntity.getId();
        this.height = clinicalEntity.getHeight();
        this.weight = clinicalEntity.getWeight();

        this.allergies = clinicalEntity.getAllergies().stream()
                .map(AllergiesModel::new).collect(Collectors.toList());
    }
}
