package com.novaperutech.veyra.platform.profiles.domain.model.commands;

import java.time.LocalDate;

public record UpdatePersonProfileCommand(Long personProfileId, String dni, String firstName, String lastName, LocalDate birthDate, Integer Age, String emailAddress, String street,
                                         String number,
                                         String city,
                                         String postalCode,
                                         String country, String photo, String phoneNumber) {
    public UpdatePersonProfileCommand{
        if (personProfileId == null){
            throw new IllegalArgumentException("personProfileId is null");
        }
        if (dni==null||dni.isBlank()){
            throw new IllegalArgumentException("dni is null");
        }
        if (firstName==null||firstName.isBlank()){
            throw new IllegalArgumentException("firstName is null");
        }
        if (lastName==null||lastName.isBlank()){
            throw new IllegalArgumentException("lastName is null");
        }
        if (birthDate==null){
            throw new IllegalArgumentException("birthDate is null");
        }
    }
}
