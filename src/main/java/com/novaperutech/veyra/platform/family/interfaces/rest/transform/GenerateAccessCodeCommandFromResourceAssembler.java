package com.novaperutech.veyra.platform.family.interfaces.rest.transform;

import com.novaperutech.veyra.platform.family.domain.model.commands.GenerateAccessCodeCommand;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.GenerateAccessCodeResource;

public class GenerateAccessCodeCommandFromResourceAssembler {
    public static GenerateAccessCodeCommand toCommandFromResource(GenerateAccessCodeResource resource){
        return new GenerateAccessCodeCommand(resource.residentId(),resource.familyEmail(),resource.validityDays());
    }
}
