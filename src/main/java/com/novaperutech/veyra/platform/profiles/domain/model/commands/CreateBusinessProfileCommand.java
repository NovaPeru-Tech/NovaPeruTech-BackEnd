package com.novaperutech.veyra.platform.profiles.domain.model.commands;

public record CreateBusinessProfileCommand(String businessName,
                                           String emailAddress,
                                           String phoneNumber,
                                           String street,
                                           String number,
                                           String city,
                                           String postalCode,
                                           String country,
                                           String photoUrl,
                                           String ruc ) {
}
