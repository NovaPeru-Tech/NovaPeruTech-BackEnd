package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateARoomToTheNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.AssignRoomForResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.ChangeOfRoomForTheResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;

@Service
public class NursingHomeCommandServiceImpl implements NursingHomeCommandServices {
    private final NursingHomeRepository nursingHomeRepository;
   private final ExternalProfileService externalProfileService;
   private final ResidentRepository residentRepository;
    public NursingHomeCommandServiceImpl(NursingHomeRepository nursingHomeRepository, ExternalProfileService externalProfileService, ResidentRepository residentRepository) {
        this.nursingHomeRepository = nursingHomeRepository;
        this.externalProfileService = externalProfileService;
        this.residentRepository = residentRepository;
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
    @Override
    public void handle(ChangeOfRoomForTheResidentCommand command) {
        var nursingHome = nursingHomeRepository.findById(command.nursingHomeId())
                .orElseThrow(() -> new IllegalArgumentException("Nursing home not found with id: " + command.nursingHomeId()));

        var resident = residentRepository.findById(command.residentId())
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + command.residentId()));

        if (residentRepository.existsByIdAndNursingHomeId(command.residentId(), command.nursingHomeId())) {
            throw new IllegalArgumentException("Resident does not exist in this nursing home");
        }

        var currentRoom = nursingHome.getRooms().getAllRooms().stream()
                .filter(room -> room.getResident() != null && room.getResident().getId().equals(resident.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Resident is not currently assigned to any room"));

        var newRoom = nursingHome.getRooms().getRoomByRoomNumber(command.newRoomNumber());
        if (newRoom == null) {
            throw new IllegalArgumentException("New room not found with number: " + command.newRoomNumber());
        }

        currentRoom.removeResident();
        newRoom.assignResident(resident);

        nursingHomeRepository.save(nursingHome);
    }

    @Override
    public void handle(AssignRoomForResidentCommand command) {
        var nursingHome=nursingHomeRepository.findById(command.nursingHomeId()).orElseThrow(()-> new IllegalArgumentException("NursingHome not found with id:" + command.nursingHomeId()));
        var room= nursingHome.getRooms().getRoomByRoomNumber(command.roomNumber());
        if (room==null){
            throw new IllegalArgumentException("Room number not found");
        }
        var resident=residentRepository.findById(command.residentId()).orElseThrow(()->new IllegalArgumentException("Resident not found with id:" + command.residentId()));
        if (!residentRepository.existsByIdAndNursingHomeId(command.residentId(), command.nursingHomeId()))
        {
            throw new IllegalArgumentException("resident dont exists in the nursing home");
        }
        room.assignResident(resident);
        nursingHomeRepository.save(nursingHome);

    }
}
