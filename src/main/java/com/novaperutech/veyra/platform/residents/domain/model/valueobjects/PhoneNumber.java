package com.novaperutech.veyra.platform.residents.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String phoneNumber) {
    public PhoneNumber {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }
        // Simple validation for phone number format (you can enhance this as needed)
        if (!phoneNumber.matches("\\+?[0-9\\- ]{7,15}")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
}
