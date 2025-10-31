package com.novaperutech.veyra.platform.residents.interfaces.rest.transform;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.ResidentResource;

public class ResidentResourceFromEntityAssembler {

    public static ResidentResource toResource(Resident entity) {
        return new ResidentResource(
                entity.getId(),
                entity.getState(),
                entity.getName(),
                entity.getLastname(),
                entity.getDni(),
                entity.getBirthDate(),
                entity.getAge(),
                entity.getImage(),
                entity.getRoom(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getBloodType(),
                entity.getAllergies(),
                entity.getChronicDiseases(),
                entity.getCurrentMedications(),
                entity.getSpecialDiet(),
                entity.getMobilityLevel(),
                entity.getDependencyLevel(),
                entity.isNeedsBathingAssistance(),
                entity.isNeedsFeedingAssistance(),
                entity.isNeedsDressingAssistance(),
                entity.getEmergencyContactName(),
                entity.getEmergencyPhone(),
                entity.getContactRelation(),
                entity.getSecondaryContact(),
                entity.getSecondaryPhone(),
                entity.getAdmissionDate(),
                entity.getAttendingPhysician(),
                entity.getMedicalInsurance(),
                entity.getSocialSecurityNumber(),
                entity.getLegalGuardian(),
                entity.getGuardianPhone()
        );
    }
}
