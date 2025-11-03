package com.novaperutech.veyra.platform.residents.interfaces.rest.transform;

import com.novaperutech.veyra.platform.residents.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.UpdateResidentResource;

public class UpdateResidentCommandFromResourceAssembler {

    public static UpdateResidentCommand toCommand(Long id, UpdateResidentResource resource) {
        return new UpdateResidentCommand(
                id,
                resource.state(),
                resource.name(),
                resource.lastname(),
                resource.dni(),
                resource.birthDate(),
                resource.age(),
                resource.image(),
                resource.room(),
                resource.phoneNumber(),
                resource.email(),
                resource.allergies(),
                resource.chronicDiseases(),
                resource.currentMedications(),
                resource.specialDiet(),
                resource.mobilityLevel(),
                resource.dependencyLevel(),
                resource.needsBathingAssistance(),
                resource.needsFeedingAssistance(),
                resource.needsDressingAssistance(),
                resource.emergencyContactName(),
                resource.emergencyPhone(),
                resource.contactRelation(),
                resource.secondaryContact(),
                resource.secondaryPhone(),
                resource.admissionDate(),
                resource.attendingPhysician(),
                resource.medicalInsurance(),
                resource.socialSecurityNumber(),
                resource.legalGuardian(),
                resource.guardianPhone()
        );
    }
}
