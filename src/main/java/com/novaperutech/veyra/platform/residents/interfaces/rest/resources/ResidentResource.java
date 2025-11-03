package com.novaperutech.veyra.platform.residents.interfaces.rest.resources;

import java.time.LocalDate;

public record ResidentResource(
        Long id,
        String state,
        String name,
        String lastname,
        String dni,
        LocalDate birthDate,
        Integer age,
        String image,
        String room,
        String phoneNumber,
        String email,
      String fullEmergencyContactName,
        String emergencyContactPhoneNumber,
        String emergencyContactRelationship,
      String fullGuardianName,
        String guardianPhone
) {}
