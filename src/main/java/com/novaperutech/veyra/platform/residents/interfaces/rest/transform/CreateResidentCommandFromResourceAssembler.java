package com.novaperutech.veyra.platform.residents.interfaces.rest.transform;

import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.CreateResidentResource;

public class CreateResidentCommandFromResourceAssembler {

    public static CreateResidentCommand toCommandFromResource(CreateResidentResource resource) {
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
                resource.emergencyContactFirstName(),
                resource.emergencyContactLastName(),
                resource.emergencyContactPhoneNumber(),
                resource.emergencyContactRelationship(),
                resource.firstGuardianName(),
                resource.lastGuardianName(),
                resource.guardianPhone()
        );
    }
}
