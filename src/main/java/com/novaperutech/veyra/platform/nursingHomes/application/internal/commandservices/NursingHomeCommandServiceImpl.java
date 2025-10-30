package com.novaperutech.veyra.platform.nursingHomes.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.DuplicateNameException;
import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.DuplicatePhoneNumberException;
import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.DuplicateRucException;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Name;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.PhoneNumber;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Ruc;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.NursingHomeCommandService;
import com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Implementation of the nursing home command service.
 * This service handles the creation of nursing home entities.
 * It enforces business rules and validates uniqueness constraints
 * before persisting data to the repository.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHomeCommandService
 * @see NursingHome
 */
@Service
public class NursingHomeCommandServiceImpl implements NursingHomeCommandService {
    /**
     * Repository for nursing home persistence operations.
     */
    private final NursingHomeRepository nursingHomeRepository;
    /**
     * Constructs a new NursingHomeCommandServiceImpl with the required repository.
     *
     * @param nursingHomeRepository the repository to manage nursing home persistence
     */
    public NursingHomeCommandServiceImpl(NursingHomeRepository nursingHomeRepository) {
        this.nursingHomeRepository = nursingHomeRepository;
    }
    /**
     * Handles the creation of a new nursing home.
     * This method validates that no duplicate name, RUC, or phone number exists
     * before creating and persisting the new nursing home entity.
     * The validation ensures data integrity and uniqueness constraints are maintained.
     *
     * @param command the {@link CreateNursingHomeCommand} containing the nursing home data
     * @return an {@link Optional} containing the created {@link NursingHome} if successful
     * @throws DuplicateNameException if a nursing home with the same name already exists
     * @throws DuplicateRucException if a nursing home with the same RUC already exists
     * @throws DuplicatePhoneNumberException if a nursing home with the same phone number already exists
     * @throws IllegalArgumentException if an error occurs while saving the nursing home
     */
    @Override
    public Optional<NursingHome> handle(CreateNursingHomeCommand command) {
        var name=new Name(command.name());
        var ruc= new Ruc(command.ruc());
        var phoneNumber= new PhoneNumber(command.phoneNumber());
        if (nursingHomeRepository.existsByName(name)){
            throw new DuplicateNameException(name.name());
        }
        else if(nursingHomeRepository.existsByRuc(ruc)){
            throw new DuplicateRucException(ruc.ruc());
        }
        else if(nursingHomeRepository.existsByPhoneNumber(phoneNumber)){
          throw new DuplicatePhoneNumberException(phoneNumber.phoneNumber());
        }
          var nursingHome= new NursingHome(command);
        try {
            nursingHomeRepository.save(nursingHome);

        }catch (Exception e){
            throw new IllegalArgumentException("Error saving nursingHome:%s".formatted(e.getMessage()));
        }
        return Optional.of(nursingHome);



    }
}
