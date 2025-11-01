package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.entities.Headquarter;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.HeadQuarterResource;
/**
 * Assembler class for transforming {@link Headquarter} entities into {@link HeadQuarterResource} DTOs.
 * <p>
 * This class follows the Assembler pattern and is responsible for converting domain entities
 * into resource representations suitable for REST API responses. It encapsulates the transformation
 * logic to maintain separation between the domain layer and the presentation layer.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see Headquarter
 * @see HeadQuarterResource
 */
public class HeadQuarterResourceFromEntityAssembler {
    /**
     * Transforms a {@link Headquarter} entity into a {@link HeadQuarterResource} DTO.
     * <p>
     * This method extracts the relevant data from the headquarter entity and creates
     * a corresponding resource object that can be serialized and returned in API responses.
     * It includes the headquarter ID, associated nursing home ID, title, and address.
     * </p>
     *
     * @param entity the headquarter entity to transform, must not be null
     * @return a {@link HeadQuarterResource} containing the entity's data in a format
     *         suitable for API responses
     * @throws NullPointerException if the entity or any of its required properties
     *                              (nursingHome, address) are null
     */
    public static HeadQuarterResource toResourceFromEntity(Headquarter entity){
        return new HeadQuarterResource( entity.getId(),entity.getNursingHome().getId(),entity.getTitle(),entity.getAddress().address());
    }
}
