package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.DescriptionCannotBeNullException;
import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.InvalidDescriptionLengthException;
import jakarta.persistence.Embeddable;
/**
 * Value object representing a nursing home description.
 * This immutable record encapsulates a description with built-in validation
 * to ensure it meets the required business rules.
 * A valid description must be between 70 and 400 characters long and cannot be null or blank.
 *
 * @param description the description as a string
 *
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
@Embeddable
public record Description(String description) {

    /**
     * Canonical constructor with validation logic.
     * Validates that the description is not null or blank and has a length
     * between 70 and 400 characters inclusive.
     * This ensures that all Description instances are valid according to business rules.
     *
     * @throws DescriptionCannotBeNullException if the description is null or blank
     * @throws InvalidDescriptionLengthException if the description length is outside the valid range (70-400 characters)
     */
public Description{
    if (description==null|| description.isBlank()){
        throw new DescriptionCannotBeNullException(description);
    }
    else if(description.length()<70||description.length()>400){
        throw new InvalidDescriptionLengthException(description);
    }
}
}
