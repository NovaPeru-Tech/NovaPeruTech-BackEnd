package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateNursingHomeResource;

public class CreateNursingHomeCommandFromResourceAssembler {
    public static CreateNursingHomeCommand toCommandFromResource(CreateNursingHomeResource resource,Long adminId)
    {
        return new CreateNursingHomeCommand(adminId,resource.businessName(),resource.emailAddress(),resource.phoneNumber(),resource.street()
        ,resource.number(),resource.city(),resource.postalCode(),resource.country(),resource.photo(),resource.ruc());
    }
}
