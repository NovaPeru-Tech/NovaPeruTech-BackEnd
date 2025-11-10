package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateNursingHomeResource;

public class CreateNursingHomeCommandFromResourceAssembler {
    public static CreateNursingHomeCommand toCommandFromResource(CreateNursingHomeResource resource)
    {
        return new CreateNursingHomeCommand(resource.businessName(),resource.emailAddress(),resource.phoneNumber(),resource.street()
        ,resource.number(),resource.city(),resource.postalCode(),resource.country(),resource.photoUrl(),resource.ruc());
    }
}
