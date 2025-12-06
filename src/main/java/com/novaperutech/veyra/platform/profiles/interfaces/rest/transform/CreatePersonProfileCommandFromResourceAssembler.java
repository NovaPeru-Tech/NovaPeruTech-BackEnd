package com.novaperutech.veyra.platform.profiles.interfaces.rest.transform;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.interfaces.rest.resources.CreatePersonProfileResource;

public class CreatePersonProfileCommandFromResourceAssembler {
    public static CreatePersonProfileCommand toCommandFromResource(CreatePersonProfileResource resource){
        return new CreatePersonProfileCommand(resource.dni()
                ,resource.firstName()
                ,resource.lastName(),
                resource.birthDate(),
                resource.age(),
                resource.emailAddress(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country(),
                resource.photo(),
                resource.phoneNumber());
    }
}