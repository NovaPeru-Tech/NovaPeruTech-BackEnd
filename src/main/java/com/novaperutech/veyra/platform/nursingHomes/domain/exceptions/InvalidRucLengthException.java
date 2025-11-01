package com.novaperutech.veyra.platform.nursingHomes.domain.exceptions;

public class InvalidRucLengthException extends RuntimeException {
    public InvalidRucLengthException(String ruc) {
        super(String.format("Invalid RUC length: expected 11 digits but got %d (value: %s)", ruc.length(), ruc));
    }
}
