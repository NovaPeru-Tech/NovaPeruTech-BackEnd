package com.novaperutech.veyra.platform.residents.interfaces.rest.resources;

import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.BloodType;

import java.time.LocalDate;
import java.util.List;

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
        BloodType bloodType,
        String allergies,
        List<String> chronicDiseases,
        String currentMedications,
        String specialDiet,
        String mobilityLevel,
        String dependencyLevel,
        boolean needsBathingAssistance,
        boolean needsFeedingAssistance,
        boolean needsDressingAssistance,
        String emergencyContactName,
        String emergencyPhone,
        String contactRelation,
        String secondaryContact,
        String secondaryPhone,
        LocalDate admissionDate,
        String attendingPhysician,
        String medicalInsurance,
        String socialSecurityNumber,
        String legalGuardian,
        String guardianPhone
) {}
