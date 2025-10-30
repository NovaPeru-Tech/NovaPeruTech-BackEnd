package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.InvalidRucLengthException;
import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.RucCannotBeNullException;
import jakarta.persistence.Embeddable;
/**
 * Value object representing a RUC (Registro Único de Contribuyentes).
 * This immutable record encapsulates the tax identification number used in Peru
 * for legal entities. It ensures that the RUC value is always valid by enforcing
 * business rules in its constructor.
 * A valid RUC must be exactly 11 characters long and cannot be null or blank.
 *
 * @param ruc the RUC tax identification number as a string
 * @author NovaPeru Tech
 * @version 1.0
 */
@Embeddable
public record Ruc(String ruc) {
    /**
     * Canonical constructor with validation logic.
     * Validates that the RUC is not null or blank and has exactly 11 characters.
     * This ensures that all Ruc instances are valid according to business rules.
     *
     * @throws RucCannotBeNullException if the RUC is null or blank
     * @throws InvalidRucLengthException if the RUC length is not exactly 11 characters
     */
    public Ruc{
        if (ruc==null||ruc.isBlank()){throw new RucCannotBeNullException(ruc);
        }
        else if (ruc.length()!=11){ throw new InvalidRucLengthException(ruc);
        }
    }
}
