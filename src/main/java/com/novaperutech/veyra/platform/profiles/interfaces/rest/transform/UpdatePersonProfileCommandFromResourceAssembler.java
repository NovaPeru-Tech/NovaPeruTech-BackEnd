package com.novaperutech.veyra.platform.profiles.interfaces.rest.transform;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.UpdatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.interfaces.rest.resources.UpdatePersonProfileResource;

public class UpdatePersonProfileCommandFromResourceAssembler {
    public static UpdatePersonProfileCommand toCommandFromResource(Long personProfileId, UpdatePersonProfileResource resource){
        return new UpdatePersonProfileCommand(personProfileId,resource.dni(),
                resource.firstName(),resource.lastName(),resource.birthDate(),resource.age(),
                resource.emailAddress(),resource.street(),resource.number(),resource.city(),resource.postalCode(),resource.country(),resource.photo()
                ,resource.phoneNumber()
        );
    }
}
