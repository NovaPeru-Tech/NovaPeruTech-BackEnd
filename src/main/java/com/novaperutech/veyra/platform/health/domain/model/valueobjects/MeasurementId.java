package com.novaperutech.veyra.platform.health.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record MeasurementId(Long measurementId) {
public MeasurementId{
    if (measurementId==null||measurementId<1){
        throw new IllegalArgumentException("measurement id cannot be null o then less 1");
    }
}
}
