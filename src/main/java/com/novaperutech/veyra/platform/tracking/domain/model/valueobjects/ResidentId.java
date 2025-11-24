package com.novaperutech.veyra.platform.tracking.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ResidentId(Long residentId) {

    public ResidentId {
        if (residentId == null || residentId <= 0) {
            throw new IllegalArgumentException("Resident ID must be a positive integer");
        }
    }
}
