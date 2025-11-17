package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.AddARoomToTheNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;

@Service
public class NursingHomeCommandServiceImpl implements NursingHomeCommandServices {
    private final NursingHomeRepository nursingHomeRepository;
   private final ExternalProfileService externalProfileService;
    public NursingHomeCommandServiceImpl(NursingHomeRepository nursingHomeRepository, ExternalProfileService externalProfileService) {
        this.nursingHomeRepository = nursingHomeRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Long handle(CreateNursingHomeCommand command) {
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
        var nursingHome= new NursingHome(businessProfileIde.get());
        nursingHomeRepository.save(nursingHome);
        return nursingHome.getId();

    }

    @Override
    public void handle(AddARoomToTheNursingHomeCommand command) {
        nursingHomeRepository.findById(command.nursingHomeId())
                .map(nursingHome -> {
                    nursingHome.addRoom(command.capacity(), command.type());
                    nursingHomeRepository.save(nursingHome);
                    return nursingHome;
                }).orElseThrow(() -> new IllegalArgumentException("Nursing home does not exist"));
    }
}
