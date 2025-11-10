package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.DeleteResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.EmergencyContact;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.LegalRepresentative;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidentCommandServiceImpl implements ResidentCommandServices {
    private  final ExternalProfileService externalProfileService;
    private  final ResidentRepository residentRepository;
    public ResidentCommandServiceImpl(ExternalProfileService externalProfileService, ResidentRepository residentRepository) {
        this.externalProfileService = externalProfileService;
        this.residentRepository = residentRepository;
    }

    @Override
    public Long handle(CreateResidentCommand command) {
        var personProfileId= externalProfileService.fetchProfileByDni(command.dni());
        if(personProfileId.isEmpty()){
            personProfileId= externalProfileService.createPersonProfile(
                    command.dni(),command.firstName(),command.lastName(),command.birthDate(),command.Age(),command.emailAddress(),command.street()
                    ,command.number(),command.city(),command.postalCode(),command.country(),
                    command.photo(),command.phoneNumber()
            );
        }
        else {
            residentRepository.findByPersonProfileId(personProfileId.get()).ifPresent(resident->{
                var message =String.format("Resident with personProfileId %s already exists with same profile",resident.getId());
                throw new IllegalArgumentException(message);
            });
        }
        if (personProfileId.isEmpty()){
            throw new IllegalArgumentException("Unable to create resident profile ");
        }
        var emergencyContact= new EmergencyContact(command.emergencyContactFirstName(),command.emergencyContactLastName(),command.emergencyContactPhoneNumber());
        var legalRepresentative= new LegalRepresentative(command.legalRepresentativeFirstName(),command.legalRepresentativeLastName(),command.legalRepresentativePhoneNumber());
        var resident= new Resident(personProfileId.get(),legalRepresentative,emergencyContact);
        residentRepository.save(resident);
        return resident.getId();
    }

    @Override
    public Optional<Resident> handle(UpdateResidentCommand command) {
        var result = residentRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Resident with residentId " + command.id() + " does not exist");
        }

        var residentUpdate = result.get();

        try {
            externalProfileService.updatePersonProfile(
                     residentUpdate.getPersonProfileId().personProfileId(),command.dni(), command.firstName(), command.lastName(),
                    command.birthDate(), command.Age(), command.emailAddress(), command.street(),
                    command.number(), command.city(), command.postalCode(), command.country(),
                    command.photo(), command.phoneNumber()
            );

          var updatedResident = residentUpdate
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
        var result= residentRepository.findById(command.residentId());
        if (result.isEmpty()){throw new IllegalArgumentException(" Resident with residentId"+command.residentId()+" does not exist");}
        var residentDelete= result.get();
        try{
            externalProfileService.deletePersonProfile(command.residentId());
            residentRepository.delete(residentDelete);
        }catch(Exception e){
            throw new IllegalArgumentException("Error while delete resident: %s".formatted(e.getMessage()));
        }
    }


}
