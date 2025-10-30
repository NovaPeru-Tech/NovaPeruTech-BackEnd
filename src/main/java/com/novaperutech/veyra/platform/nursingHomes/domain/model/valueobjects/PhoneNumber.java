package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.PhoneNumberCannotBeNullException;
import jakarta.persistence.Embeddable;

import java.lang.module.InvalidModuleDescriptorException;

/**
 * Value object representing a phone number.
 * This immutable record encapsulates a phone number with built-in validation
 * to ensure it meets the required business rules.
 * A valid phone number must be between 9 and 12 characters long and cannot be null or blank.
 *
 * @param phoneNumber the phone number as a string
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
@Embeddable
public record PhoneNumber(String phoneNumber) {

    /**
     * Canonical constructor with validation logic.
     * Validates that the phone number is not null or blank and has a length
     * between 9 and 12 characters inclusive.
     * This ensures that all PhoneNumber instances are valid according to business rules.
     *
     * @throws PhoneNumberCannotBeNullException if the phone number is null or blank
     * @throws InvalidModuleDescriptorException if the phone number length is outside the valid range (9-12 characters)
     */
    public PhoneNumber{
        if (phoneNumber==null||phoneNumber.isBlank()){
            throw new PhoneNumberCannotBeNullException(phoneNumber);
        }
        else if (phoneNumber.length()<9||phoneNumber.length()>12){
            throw new InvalidModuleDescriptorException();
        }
    }
}
