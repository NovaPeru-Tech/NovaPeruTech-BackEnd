package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateResidentResource(String dni, String firstName, String lastName,
                                     LocalDate birthDate, Integer age, String emailAddress, String street,
                                     String number,
                                     String city,
                                     String postalCode,
                                     String country,
                                     String photo, String phoneNumber, String legalRepresentativeFirstName
        , String legalRepresentativeLastName, String legalRepresentativePhoneNumber
        , String emergencyContactFirstName, String emergencyContactLastName, String emergencyContactPhoneNumber) {
}
