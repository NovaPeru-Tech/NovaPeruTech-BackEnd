package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class PhoneNumberCannotBeNullException extends RuntimeException {
    public PhoneNumberCannotBeNullException(String phoneNumber) {
        super(String.format(" Phone number must not be null.",phoneNumber));
    }
}
