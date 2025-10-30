package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.NameCannotBeNullException;
import jakarta.persistence.Embeddable;
/**
 * Value object representing a nursing home name.
 * This immutable record encapsulates a name with built-in validation
 * to ensure it meets the required business rules.
 * A valid name must be between 6 and 30 characters long and cannot be null or blank.
 *
 * @param name the name as a string
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
@Embeddable
public record Name(String name) {
    /**
     * Canonical constructor with validation logic.
     * Validates that the name is not null or blank and has a length
     * between 6 and 30 characters inclusive.
     * This ensures that all Name instances are valid according to business rules.
     *
     * @throws NameCannotBeNullException if the name is null or blank
     * @throws IllegalArgumentException if the name length is outside the valid range (6-30 characters)
     */
    public Name{
        if (name==null||name.isBlank()){
throw new NameCannotBeNullException(name);
        } else if (name.length() < 6 || name.length() > 30) {
         throw new IllegalArgumentException("Name length must be between 6 and 12 characters");
        }
     }
}
