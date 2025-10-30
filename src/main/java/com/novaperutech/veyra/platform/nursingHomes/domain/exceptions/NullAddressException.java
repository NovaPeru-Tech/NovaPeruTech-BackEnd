package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class NullAddressException extends RuntimeException {
    public NullAddressException(String address) {
        super(String.format("Address must not be null. ",address));
    }
}
