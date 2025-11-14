package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

import java.time.LocalDateTime;

public record MedicationAdministrationResource(Long id , Long residentId, Long staffMemberId,String medicationName, LocalDateTime administeredAt, Integer quantityAdministered, boolean wasAdministered, String notes) {
}
