package com.novaperutech.veyra.platform.profiles.interfaces.rest.transform;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreateBusinessProfileCommand;
import com.novaperutech.veyra.platform.profiles.interfaces.rest.resources.CreateBusinessProfileResource;

public class CreateBusinessProfileCommandFromResourceAssembler {
    public static CreateBusinessProfileCommand toCommandFromResource(CreateBusinessProfileResource resource){
        return new CreateBusinessProfileCommand(
                resource.businessName(),
                resource.emailAddress(),
                resource.phoneNumber(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country(),
                resource.photoUrl(),
                resource.ruc()        );
    }
}
