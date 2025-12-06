package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateARoomToTheNursingHomeCommand;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.AdministratorRepository;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;

@Service
public class NursingHomeCommandServiceImpl implements NursingHomeCommandServices {
    private final NursingHomeRepository nursingHomeRepository;
   private final ExternalProfileService externalProfileService;
   private final AdministratorRepository administratorRepository;
    public NursingHomeCommandServiceImpl(NursingHomeRepository nursingHomeRepository, ExternalProfileService externalProfileService,  AdministratorRepository administratorRepository) {
        this.nursingHomeRepository = nursingHomeRepository;
        this.externalProfileService = externalProfileService;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Long handle(CreateNursingHomeCommand command) {
        var administratorId= administratorRepository.findById(command.administratorId()).orElseThrow(()->new IllegalArgumentException("administrator dont exists"));
        var existingNursingHome= nursingHomeRepository.findByAdministratorId(administratorId.getId());
        if (existingNursingHome.isPresent()){
            throw new IllegalArgumentException("Nursing home already exists for administrator with id: " + command.administratorId());
        }

        var businessProfileIde= externalProfileService.fetchBusinessProfileByRuc(command.ruc());
        if (businessProfileIde.isEmpty()){
           businessProfileIde= externalProfileService.createBusinessProfile(
                    command.businessName(),command.emailAddress(),command.phoneNumber(),command.street(),command.number(),command.city(),
                    command.postalCode(),command.country(),command.photoUrl(),command.ruc());
        }
        else {
            nursingHomeRepository.findByBusinessProfileId(businessProfileIde.get()).ifPresent(nursingHome ->
            {
                var message =String.format("Nursing home with nursing ProfileId %s already exists with same profile",nursingHome.getId());
                throw new IllegalArgumentException(message);
            });
        }

        if (businessProfileIde.isEmpty()){
            throw new IllegalArgumentException("Unable to create business profile");
        }
        var nursingHome= new NursingHome(businessProfileIde.get(),administratorId);
        nursingHomeRepository.save(nursingHome);
        return nursingHome.getId();

    }

    @Override
    public void handle(CreateARoomToTheNursingHomeCommand command) {
        var nursingHome = nursingHomeRepository.findById(command.nursingHomeId())
                .orElseThrow(() -> new IllegalArgumentException("Nursing home does not exist"));

        try {
            nursingHome.addRoom(command.capacity(), command.type(), command.roomNumber());
            nursingHomeRepository.save(nursingHome);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error while adding room: %s".formatted(e.getMessage()));
        }
    }

}
