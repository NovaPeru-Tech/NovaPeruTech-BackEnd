package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.CreateNursingHomeResource;
/**
 * Assembler class for converting nursing home resources to commands.
 * This class provides static methods to transform REST API resources into
 * domain commands, following the Assembler pattern. It acts as a bridge
 * between the presentation layer and the application layer.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see CreateNursingHomeResource
 * @see CreateNursingHomeCommand
 */
public class CreateNursingHomeCommandFromResourceAssembler {
    /**
     * Converts a CreateNursingHomeResource to a CreateNursingHomeCommand.
     * This method extracts all necessary data from the resource and creates
     * a corresponding command object that can be processed by the application layer.
     *
     * @param resource the {@link CreateNursingHomeResource} containing the nursing home data from the REST API
     * @return a {@link CreateNursingHomeCommand} containing the same data ready for processing
     */
    public static CreateNursingHomeCommand toCommandFromResource(CreateNursingHomeResource resource){
        return new CreateNursingHomeCommand(resource.name(),resource.ruc(),resource.phoneNumber(),resource.address(),resource.description());
    }
}
