package com.novaperutech.veyra.platform.residents.interfaces.rest.transform;

import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.CreateResidentResource;

public class CreateResidentCommandFromResourceAssembler {

    public static CreateResidentCommand toCommand(CreateResidentResource resource) {
        return new CreateResidentCommand(
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
                resource.bloodType(),
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
