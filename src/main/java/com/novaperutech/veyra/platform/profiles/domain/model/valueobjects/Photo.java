package com.novaperutech.veyra.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Lob;

public record Photo(@Lob String photo) {
    public Photo {
        if (photo == null || photo.isBlank()) {
            throw new IllegalArgumentException("Photo cannot be null or blank");
        }
    }
}
