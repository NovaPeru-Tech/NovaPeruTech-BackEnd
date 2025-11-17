package com.novaperutech.veyra.platform.profiles.domain.model.valueobjects;

public record Photo(String photo) {
    public Photo {
        if (photo == null || photo.isBlank()) {
            throw new IllegalArgumentException("Photo cannot be null or blank");
        }
    }
}
