package com.novaperutech.veyra.platform.profiles.interfaces.rest.resources;

public record CreateBusinessProfileResource(String businessName,String emailAddress,String phoneNumber, String street,
                                            String number,
                                            String city,
                                            String postalCode,
                                            String country, String photoUrl, String ruc) {
}
