package com.example.springboot_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springboot_assignment.entity.PatientEntity;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>{
    List<PatientEntity> findByFirstName(String firstName);
    List<PatientEntity> findByFirstNameAndLastName(String firstName, String lastName);

}
