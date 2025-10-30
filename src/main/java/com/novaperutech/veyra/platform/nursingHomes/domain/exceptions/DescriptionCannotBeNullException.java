package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class DescriptionCannotBeNullException extends RuntimeException {
    public DescriptionCannotBeNullException(String description) {
        super(String.format("Description must not be null",description));
    }
}
