package com.novaperutech.veyra.platform.family.domain.model.commands;

public record GenerateAccessCodeCommand( Long residentId,
                                         String familyEmail,
                                         Integer validityDays ) {
}
