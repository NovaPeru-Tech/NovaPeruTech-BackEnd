package com.novaperutech.veyra.platform.profiles.application.internal.commandservices;

import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.PersonProfile;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.DeletePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.UpdatePersonProfileCommand;
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

    @Override
    public Optional<PersonProfile> handle(UpdatePersonProfileCommand command) {
        var result = personProfileRepository.findById(command.personProfileId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Could not find person profile with id " + command.personProfileId());
        }
        var personProfileUpdate = result.get();
        try {
            var updatedPersonProfile = personProfileRepository.save(personProfileUpdate.updatePersonProfile(
                    command.dni(), command.firstName(), command.lastName(), command.birthDate(), command.Age()
                    , command.emailAddress(), command.street(), command.number(), command.city(), command.postalCode(), command.country()
                    , command.photo(), command.phoneNumber()));
            return Optional.of(updatedPersonProfile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating person profile:%s".formatted(e.getMessage()));
        }

    }
    @Override
    public void handle(DeletePersonProfileCommand command) {
        if(!personProfileRepository.existsById(command.personProfileId())) {
            throw new IllegalArgumentException("Could not find person profile with id "+command.personProfileId());
        }
        try {
            personProfileRepository.deleteById(command.personProfileId());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while delete person profile:%s");
        }
    }

}
