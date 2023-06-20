package com.example.springboot_assignment.constants;

public class LogConstants {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String PATIENT_CREATED = "New patient entry created successfully: ";
    public static final String PATIENT_UPDATED = "Existing patient updated successfully: ";
    public static final String PATIENT_DELETED = "Existing patient deleted successfully: ";
    public static final String INTERNAL_ERROR = "Internal server error occurred : ";
    public static final String ALL_PATIENTS_DATA_FOUND = "All patient's data found successfully: ";
    public static final String PATIENTS_FOUND_BY_FIRSTNAME = "All patient's data having first name as following found successfully: ";
    public static final String PATIENTS_FOUND_BY_FIRSTNAME_LASTNAME = "All patient's data having first and last name as following found successfully: ";
    public static final String PATIENTS_FOUND = "patient's data with the given ID found successfully: ";
    public static final String PATIENT_NOT_CREATED = "Failed to create new patient. Dao Layer returned null patient entity";
    public static final String PATIENT_NOT_UPDATED = "Failed to update existing patient. Dao Layer returned null patient entity";
    public static final String PATIENT_NOT_DELETED = "Failed to delete existing patient. Dao Layer returned null patient entity";
    public static final String PATIENTS_NOT_FOUND = "Currently there is no patient data exists in database";
    public static final String ENTITY_MODEL_CONVERSION = "Patient Entities converted into Patient Models: ";
    public static final String PATIENT_NOT_FOUND = "Patients does not exist ";
    public static final String INVALID_PATIENT_ID = "Patient ID is invalid : Required Long found String :";
    public static final String FIRST_NAME_EMPTY = "First Name field can not be null :";
    public static final String LAST_NAME_EMPTY = "Last Name field can not be null :";
    public static final String PATIENT_ALREADY_EXIST = "Patient with following firstname and lastname already exists : ";


}