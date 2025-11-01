package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class InvalidDescriptionLengthException extends RuntimeException {
    public InvalidDescriptionLengthException(String description) {
        super(String.format("Invalid Description length: expected 11 digits but got %d (value: %s)",description.length(),description));
    }
}
