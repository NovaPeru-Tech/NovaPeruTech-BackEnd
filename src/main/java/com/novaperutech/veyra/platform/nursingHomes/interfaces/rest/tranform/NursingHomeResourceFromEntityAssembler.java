package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.NursingHomeResource;
/**
 * Assembler class for converting nursing home entities to resources.
 * This class provides static methods to transform domain entities into
 * REST API resources, following the Assembler pattern. It acts as a bridge
 * between the application layer and the presentation layer.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHome
 * @see NursingHomeResource
 */
public class NursingHomeResourceFromEntityAssembler {
    /**
     * Converts a NursingHome entity to a NursingHomeResource.
     * This method extracts all necessary data from the entity, including unwrapping
     * value objects, and creates a corresponding resource object suitable for
     * REST API responses.
     *
     * @param entity the {@link NursingHome} entity containing the nursing home data
     * @return a {@link NursingHomeResource} containing the entity data in a format suitable for API responses
     */
    public static NursingHomeResource toCommandFromEntity(NursingHome entity){
return new NursingHomeResource(entity.getId(),entity.getName().name(),entity.getRuc().ruc(),entity.getPhoneNumber().phoneNumber(),entity.getAddress().address(),entity.getDescription().description());

    }

}
