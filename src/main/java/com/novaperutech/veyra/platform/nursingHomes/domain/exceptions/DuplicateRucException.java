package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class DuplicateRucException extends RuntimeException {
    public DuplicateRucException(String ruc) {
        super(String.format("Duplicate RUC: a nursing home with RUC '%s' already exists.", ruc));
    }
}
