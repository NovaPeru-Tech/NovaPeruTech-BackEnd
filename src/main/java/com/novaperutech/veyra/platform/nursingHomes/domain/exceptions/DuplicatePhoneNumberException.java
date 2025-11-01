package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class DuplicatePhoneNumberException extends RuntimeException {
    public DuplicatePhoneNumberException(String phoneNumber) {
        super(String.format("Phone number %s already exists", phoneNumber));
    }
}
