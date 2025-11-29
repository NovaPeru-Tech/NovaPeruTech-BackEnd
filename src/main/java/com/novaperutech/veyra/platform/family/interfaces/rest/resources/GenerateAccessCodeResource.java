package com.novaperutech.veyra.platform.family.interfaces.rest.resources;

public record GenerateAccessCodeResource(Long residentId,
                                         String familyEmail,
                                         Integer validityDays) {
}
