package com.novaperutech.veyra.platform.tracking.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OxygenSaturation(Integer value) {

    public OxygenSaturation {
        if (value == null || value < 0 || value > 100) {
            throw new IllegalArgumentException("Oxygen saturation must be between 0 and 100%");
        }
    }
}