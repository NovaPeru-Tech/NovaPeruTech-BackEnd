package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.*;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.*;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidentCommandServiceImpl implements ResidentCommandServices {
    private  final ExternalProfileService externalProfileService;
    private  final ResidentRepository residentRepository;
    private final NursingHomeRepository nursingHomeRepository;
    public ResidentCommandServiceImpl(ExternalProfileService externalProfileService, ResidentRepository residentRepository, NursingHomeRepository nursingHomeRepository) {
        this.externalProfileService = externalProfileService;
        this.residentRepository = residentRepository;
        this.nursingHomeRepository = nursingHomeRepository;
    }

    @Override
    public Long handle(CreateResidentCommand command) {
        var nursingHome = nursingHomeRepository.findById(command.nursingHomeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nursing home not found with id: " + command.nursingHomeId()));

        var personProfileId = externalProfileService.fetchProfileByDni(command.dni());

        if (personProfileId.isEmpty()) {
            personProfileId = externalProfileService.createPersonProfile(
                    command.dni(),
                    command.firstName(),
                    command.lastName(),
                    command.birthDate(),
                    command.age(),
                    command.emailAddress(),
                    command.street(),
                    command.number(),
                    command.city(),
                    command.postalCode(),
                    command.country(),
                    command.photo(),
                    command.phoneNumber()
            );

            if (personProfileId.isEmpty()) {
                throw new IllegalArgumentException("Unable to create person profile");
            }
        }

        if (residentRepository.existsByNursingHomeIdAndPersonProfileId(
                command.nursingHomeId(),
                personProfileId.get())) {
            throw new IllegalArgumentException("Resident already exists in this nursing home");
        }

        var emergencyContact = new EmergencyContact(
                command.emergencyContactFirstName(),
                command.emergencyContactLastName(),
                command.emergencyContactPhoneNumber()
        );

        var legalRepresentative = new LegalRepresentative(
                command.legalRepresentativeFirstName(),
                command.legalRepresentativeLastName(),
                command.legalRepresentativePhoneNumber()
        );
        var resident = new Resident(
                nursingHome,
                personProfileId.get(),
                legalRepresentative,
                emergencyContact
        );

        residentRepository.save(resident);
        return resident.getId();
    }

    @Override
    public Optional<Resident> handle(UpdateResidentCommand command) {
        var resident =residentRepository.findById(command.id()).orElseThrow(()-> new IllegalArgumentException("Resident not found with id:" + command.id()));
        try {
            externalProfileService.updatePersonProfile(
                     resident.getPersonProfileId().personProfileId(),command.dni(), command.firstName(), command.lastName(),
                    command.birthDate(), command.Age(), command.emailAddress(), command.street(),
                    command.number(), command.city(), command.postalCode(), command.country(),
                    command.photo(), command.phoneNumber()
            );

          var updatedResident = resident
                    .updateLegalRepresentative(
                            command.legalRepresentativeFirstName(),
                            command.legalRepresentativeLastName(),
                            command.legalRepresentativePhoneNumber()
                    ).updateEmergencyContact(
                            command.emergencyContactFirstName(),
                            command.emergencyContactLastName(),
                            command.emergencyContactPhoneNumber()
                    );

            var savedResident = residentRepository.save(updatedResident);
            return Optional.of(savedResident);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating resident: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void handle(DeleteResidentCommand command) {
        var resident= residentRepository.findById(command.residentId()).orElseThrow(()->new IllegalArgumentException("Resident not found"));
        try{
            externalProfileService.deletePersonProfile(resident.getPersonProfileId().personProfileId());
            residentRepository.delete(resident);
        }catch(Exception e){
            throw new IllegalArgumentException("Error while delete resident: %s".formatted(e.getMessage()));
        }
    }



    @Override
    public void handle(AssignedStaffMemberToResidentCommand command) {
    }

    @Override
    public void handle(ChangeOfRoomForTheResidentCommand command) {

    }

    @Override
    public void handle(AssignRoomForResidentCommand command) {

    }
}
