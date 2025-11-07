package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

import java.time.LocalDate;

public record ResidentResource(Long id, Long personProfileId,String legalRepresentativeFirstName
        , String legalRepresentativeLastName, String legalRepresentativePhoneNumber
        , String emergencyContactFirstName, String emergencyContactLastName, String emergencyContactPhoneNumber) {
}
