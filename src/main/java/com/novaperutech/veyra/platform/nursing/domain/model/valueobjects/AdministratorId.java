package com.novaperutech.veyra.platform.nursing.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AdministratorId(Long residentId) {
    public AdministratorId{
        if (residentId==null){
            throw new IllegalArgumentException("residentId cannot be null");
        }
        if (residentId<1){
            throw new IllegalArgumentException("residentId cannot less then 1");
        }
    }
}
