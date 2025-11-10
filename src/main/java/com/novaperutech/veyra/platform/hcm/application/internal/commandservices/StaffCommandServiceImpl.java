package com.novaperutech.veyra.platform.hcm.application.internal.commandservices;

import com.novaperutech.veyra.platform.hcm.application.internal.outboundservices.acl.ExternalProfileService;
import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.commands.*;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.EmergencyContact;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffCommandServices;
import com.novaperutech.veyra.platform.hcm.infrastructure.persistence.jpa.repositories.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StaffCommandServiceImpl implements StaffCommandServices {
    private final StaffRepository staffRepository;
    private final ExternalProfileService externalProfileService;
    public StaffCommandServiceImpl(StaffRepository staffRepository, ExternalProfileService externalProfileService) {
        this.staffRepository = staffRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Long handle(CreateStaffCommand command) {
        var personProfileId= externalProfileService.fetchProfileByDni(command.dni());
        if (personProfileId.isEmpty()){
            personProfileId= externalProfileService.createPersonProfile(command.dni(),command.firstName(),command.lastName(),command.birthDate(),command.Age()
            , command.emailAddress(),command.street(),command.number(),command.city(),command.postalCode(),command.country(),command.photo(),command.phoneNumber());
        }
        else{
         staffRepository.findByPersonProfileId(personProfileId.get()).ifPresent(staff->{
             var message=String.format("Staff with personProfileId %s already exists with same profile",staff.getId());
             throw new IllegalArgumentException(message);
         });
        }

       if (personProfileId.isEmpty()){
          throw new IllegalArgumentException("Unable to create person profile.");
        }
        var emergencyContact= new EmergencyContact(command.emergencyContactFirstName(),command.emergencyContactLastName(),command.emergencyContactPhoneNumber());

        var staff=new Staff(personProfileId.get(),emergencyContact);
        staffRepository.save(staff);
        return staff.getId();
    }

    @Override
    public Optional<Staff> handle(UpdateStaffCommand command) {
        var staffId=staffRepository.findById(command.id());
        if (staffId.isEmpty()){
            throw new IllegalArgumentException("Employee with residentId " + command.id() + " does not exist");
        }
        var staffUpdate=staffId.get();
    try {
        externalProfileService.updatePersonProfile(staffId.get().getPersonProfileId().id(), command.dni(), command.firstName(), command.lastName(),
                command.birthDate(), command.Age(), command.emailAddress(), command.street(),
                command.number(), command.city(), command.postalCode(), command.country(),
                command.photo(), command.phoneNumber());
        var updateStaff= staffUpdate.updateEmergencyContact(command.emergencyContactFirstName(),command.emergencyContactLastName(),command.emergencyContactPhoneNumber());
        var savedStaff= staffRepository.save(updateStaff);
        return Optional.of(savedStaff);
    }catch (Exception e){
        throw new IllegalArgumentException("Error while updating staff: %s".formatted(e.getMessage()));
    }
    }

    @Override
    public void handle(DeleteStaffCommand command) {

    }

    @Override
    public Long handle(AddContractToStaffCommand command) {
        if (!staffRepository.existsById(command.staffId())){
            throw new IllegalArgumentException("Staff with id %s not found ".formatted(command.staffId()));
        }
        else {
            try {
                Staff staff = staffRepository.findById(command.staffId())
                        .orElseThrow(() -> new IllegalArgumentException("Staff with id %s not found".formatted(command.staffId())));
                staff.addContractToHistory(command.startDate(),command.endDate(),command.typeOfContract()
                        ,command.staffRole(),command.workShift());
                staffRepository.save(staff);
                return staff.getId();
            } catch (Exception e){
                throw new IllegalArgumentException("Error while adding contract to staff: %s".formatted(e.getMessage()));
            }
        }
    }

    @Override
    public void handle(UpdateContractStatusCommand command) {
        try {
            Staff staff = staffRepository.findById(command.staffMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("Staff with id %s not found".formatted(command.staffMemberId())));
            staff.updateContractStatus(command.contractId(), command.newStatus());
            staffRepository.save(staff);
        } catch (IllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating contract status: %s".formatted(e.getMessage()));
        }
    }
}
