package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateHeadQuarterCommand;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.CreateHeadQuarterResource;

/**
 * Assembler class for transforming {@link CreateHeadQuarterResource} DTOs into {@link CreateHeadQuarterCommand} objects.
 * <p>
 * This class follows the Assembler pattern and is responsible for converting REST API request
 * resources into domain commands. It bridges the presentation layer and the domain layer,
 * ensuring proper separation of concerns by transforming incoming API data into command
 * objects that can be processed by the domain services.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see CreateHeadQuarterResource
 * @see CreateHeadQuarterCommand
 */
public class CreateHeadQuarterCommandFromResourceAssembler {
    /**
     * Transforms a {@link CreateHeadQuarterResource} DTO into a {@link CreateHeadQuarterCommand}.
     * <p>
     * This method combines the nursing home ID (typically from the URL path parameter)
     * with the resource data (from the request body) to create a complete command object
     * that encapsulates the user's intention to create a new headquarter.
     * </p>
     *
     * @param nursingHomeId the unique identifier of the nursing home to which the new
     *                      headquarter will belong, typically extracted from the URL path
     * @param resource      the resource containing the headquarter creation data (title and address)
     *                      from the request body, must not be null
     * @return a {@link CreateHeadQuarterCommand} containing all necessary information
     *         to create a new headquarter
     * @throws NullPointerException if the resource or any of its required properties are null
     */
    public static CreateHeadQuarterCommand toCommandFromResource(Long nursingHomeId, CreateHeadQuarterResource resource){
        return new CreateHeadQuarterCommand( nursingHomeId,resource.title(),resource.address());
    }
}
