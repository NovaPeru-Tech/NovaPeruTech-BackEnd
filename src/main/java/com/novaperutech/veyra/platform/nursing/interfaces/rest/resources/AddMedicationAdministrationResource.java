package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AddMedicationAdministrationResource( String medicationName, Long staffMemberId,
                                                  LocalDateTime administeredAt, Integer quantityAdministered, boolean wasAdministered, String notes) {
}
