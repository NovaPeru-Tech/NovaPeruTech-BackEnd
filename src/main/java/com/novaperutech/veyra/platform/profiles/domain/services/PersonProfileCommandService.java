package com.novaperutech.veyra.platform.profiles.domain.services;

import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.PersonProfile;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;

import java.util.Optional;

public interface PersonProfileCommandService {
    Optional<PersonProfile> handle(CreatePersonProfileCommand command);
}
