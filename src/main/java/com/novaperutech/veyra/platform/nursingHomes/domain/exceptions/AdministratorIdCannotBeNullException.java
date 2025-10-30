package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class AdministratorIdCannotBeNullException extends RuntimeException {
    public AdministratorIdCannotBeNullException() {
        super(String.format("Administrator id must not be null."));
    }
}
