package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String name) {
        super(String.format("Duplicate NAME: a nursing home with Name '%s' already exists.", name));
    }
}
