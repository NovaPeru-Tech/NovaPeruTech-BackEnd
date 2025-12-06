package com.novaperutech.veyra.platform.hcm.interfaces.rest.transform;

import com.novaperutech.veyra.platform.hcm.domain.model.commands.UpdateStaffCommand;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.UpdateStaffResource;

public class UpdateStaffCommandFromAssembler {
    public static UpdateStaffCommand toCommandFromResource(Long id, UpdateStaffResource resource){
        return new UpdateStaffCommand(id,resource.dni(),resource.firstName()
                ,resource.lastName(),resource.birthDate(),resource.age()
                ,resource.emailAddress(),resource.street(),resource.number(),resource.city()
                ,resource.postalCode(),resource.country(),resource.photo(),resource.phoneNumber(),resource.emergencyContactFirstName(),resource.emergencyContactLastName(),resource.phoneNumber());
    }
}
