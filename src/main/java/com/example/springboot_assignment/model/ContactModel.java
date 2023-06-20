package com.example.springboot_assignment.model;

import com.example.springboot_assignment.entity.ContactEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactModel {
    private long id;
    private String contactType;
    private String value;

    public ContactModel(ContactEntity contactEntity){
        this.id = contactEntity.getId();
        this.contactType = contactEntity.getContactType();
        this.value = contactEntity.getValue();
    }
}
