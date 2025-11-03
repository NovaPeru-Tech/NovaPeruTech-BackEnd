package com.novaperutech.veyra.platform.residents.interfaces.rest.resources;


import java.time.LocalDate;

public record CreateResidentResource(String state, String name,String lastname,String dni, LocalDate birthDate,
        Integer age,
        String image,
        String room,
        String phoneNumber,
        String email,
        String emergencyContactFirstName,
        String emergencyContactLastName,
        String emergencyContactPhoneNumber,
        String emergencyContactRelationship,
        String firstGuardianName,
        String lastGuardianName,
        String guardianPhone
) {}
