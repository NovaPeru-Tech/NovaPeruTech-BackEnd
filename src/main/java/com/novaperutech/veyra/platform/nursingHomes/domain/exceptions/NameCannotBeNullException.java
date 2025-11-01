package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class NameCannotBeNullException extends RuntimeException {
    public NameCannotBeNullException(String name) {
        super(String.format("Name must not be null",name));
    }
}
