package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.UpdateResidentResource;

public class UpdateResidentCommandFromResourceAssembler {
    public static UpdateResidentCommand toCommandFromResource(Long residentId,UpdateResidentResource resource){
        return new UpdateResidentCommand(residentId,resource.dni(),resource.firstName(),
        resource.lastName(),resource.birthDate(),resource.age(),resource.emailAddress(),
        resource.street(),resource.number(),resource.city(),resource.postalCode(),resource.country(),resource.photo(),resource.phoneNumber()
        ,resource.legalRepresentativeFirstName(),resource.legalRepresentativeLastName()
        ,resource.legalRepresentativePhoneNumber()
        ,resource.emergencyContactFirstName()
        ,resource.emergencyContactLastName() ,resource.emergencyContactPhoneNumber());

    }
}
