package com.novaperutech.veyra.platform.hcm.interfaces.rest.transform;

import com.novaperutech.veyra.platform.hcm.domain.model.commands.CreateStaffCommand;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.CreateStaffResource;

public class CreateStaffCommandFromResourceAssembler {
    public static CreateStaffCommand toCommandFromResource(CreateStaffResource resource)
    {
        return new CreateStaffCommand(resource.dni(),resource.firstName(),resource.lastName(),resource.birthDate(),resource.Age(),resource.emailAddress()
        ,resource.street(),resource.number(),resource.city(),resource.postalCode(),resource.country(),resource.photo(),resource.phoneNumber(),
                resource.emergencyContactFirstName(),resource.emergencyContactLastName(),resource.emergencyContactPhoneNumber());
    }
}
