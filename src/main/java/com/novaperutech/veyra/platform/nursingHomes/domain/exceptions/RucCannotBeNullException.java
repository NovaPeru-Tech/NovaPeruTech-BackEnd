package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class RucCannotBeNullException extends RuntimeException {
    public RucCannotBeNullException(String ruc) {
        super(String.format("Ruc must not be null.",ruc));
    }
}
