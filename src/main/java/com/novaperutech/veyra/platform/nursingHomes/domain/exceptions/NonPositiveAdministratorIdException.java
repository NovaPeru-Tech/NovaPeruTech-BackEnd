package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class NonPositiveAdministratorIdException extends RuntimeException {
    public NonPositiveAdministratorIdException(Long Id) {
        super(String.format("Administrator id must be positive",Id));
    }
}
