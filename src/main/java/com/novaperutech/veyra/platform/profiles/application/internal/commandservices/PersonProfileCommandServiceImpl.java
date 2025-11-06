package com.novaperutech.veyra.platform.profiles.application.internal.commandservices;

import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.PersonProfile;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.services.PersonProfileCommandService;
import com.novaperutech.veyra.platform.profiles.infrastructure.persistence.jpa.repositories.PersonProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonProfileCommandServiceImpl implements PersonProfileCommandService {
    private final PersonProfileRepository personProfileRepository;

    public PersonProfileCommandServiceImpl(PersonProfileRepository personProfileRepository) {
        this.personProfileRepository = personProfileRepository;
    }

    @Override
    public Optional<PersonProfile> handle(CreatePersonProfileCommand command) {
        var personProfile= new PersonProfile(command);
      try {
          personProfileRepository.save(personProfile);
      }catch (Exception e) {
          throw new IllegalArgumentException("Could not save person profile");
      }
      return Optional.of(personProfile);
    }
}
