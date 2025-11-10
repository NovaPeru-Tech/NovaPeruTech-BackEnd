package com.novaperutech.veyra.platform.hcm.interfaces.rest.resources;


public record StaffResource(Long id, Long profileId,
         String emergencyContactFirstName, String emergencyContactLastName, String emergencyContactPhoneNumber) {
}
