package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateResidentResource(String dni, String firstName, String lastName,
                                     LocalDate birthDate, Integer age, String emailAddress, String street,
                                     String number,
                                     String city,
                                     String postalCode,
                                     String country,
                                     String photo, String phoneNumber, String legalRepresentativeFirstName
        , String legalRepresentativeLastName, String legalRepresentativePhoneNumber
        , String emergencyContactFirstName,
                                     String emergencyContactLastName,
                                     String emergencyContactPhoneNumber) {
    public UpdateResidentResource{
        if (legalRepresentativeFirstName==null|| legalRepresentativeFirstName.isBlank()){
            throw new IllegalArgumentException("");
        }
        if (legalRepresentativeLastName==null|| legalRepresentativeLastName.isBlank()){
            throw  new IllegalArgumentException("");
        }
        if (emergencyContactFirstName==null|| emergencyContactFirstName.isBlank()){throw  new IllegalArgumentException("");}
        if (emergencyContactLastName==null|| emergencyContactLastName.isBlank()){throw new IllegalArgumentException("");}
        if (emergencyContactPhoneNumber==null|| emergencyContactPhoneNumber.isBlank()){throw new IllegalArgumentException("");}

    }
}
