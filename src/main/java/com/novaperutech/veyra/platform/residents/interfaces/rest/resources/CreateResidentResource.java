package com.novaperutech.veyra.platform.residents.interfaces.rest.resources;

import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.BloodType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record CreateResidentResource(
        @NotBlank(message = "El estado es obligatorio")
        String state,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastname,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
        String dni,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        LocalDate birthDate,

        @Min(value = 0, message = "La edad no puede ser negativa")
        Integer age,

        String image,
        String room,

        @Pattern(regexp = "^\\+?\\d{9,15}$", message = "El número de teléfono no es válido")
        String phoneNumber,

        @Email(message = "El correo no es válido")
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

        @NotBlank(message = "El contacto de emergencia es obligatorio")
        String emergencyContactName,

        @NotBlank(message = "El teléfono de emergencia es obligatorio")
        String emergencyPhone,

        @NotBlank(message = "La relación con el contacto es obligatoria")
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
