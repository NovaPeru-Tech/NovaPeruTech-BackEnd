package com.novaperutech.veyra.platform.nursing.interfaces.rest.resources;

public record CreateNursingHomeResource(String businessName,
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
