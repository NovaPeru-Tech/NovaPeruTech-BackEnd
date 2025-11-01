package com.novaperutech.veyra.platform.nursingHomes.application.internal.commandservices;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateHeadQuarterCommand;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.entities.Headquarter;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Address;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.HeadQuarterCommandService;
import com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories.HeadQuarterRepository;
import com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;
/**
 * Implementation of {@link HeadQuarterCommandService} for handling headquarter command operations.
 * <p>
 * This service implements the command side of the CQRS pattern, providing write operations
 * for managing headquarters. It orchestrates the creation of new headquarter entities,
 * validates business rules, and coordinates persistence operations across multiple repositories.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see HeadQuarterCommandService
 * @see HeadQuarterRepository
 * @see NursingHomeRepository
 */
@Service
public class HeadQuarterCommandServiceImpl implements HeadQuarterCommandService {

    /**
     * Repository for accessing and persisting headquarter data.
     */
    private final HeadQuarterRepository headQuarterRepository;

    /**
     * Repository for accessing nursing home data.
     */
    private final NursingHomeRepository nursingHomeRepository;
    /**
     * Constructs a new HeadQuarterCommandServiceImpl with the required repositories.
     *
     * @param headQuarterRepository the repository for headquarter data access and persistence,
     *                              injected by Spring's dependency injection
     * @param nursingHomeRepository the repository for nursing home data access,
     *                              injected by Spring's dependency injection
     */

    public HeadQuarterCommandServiceImpl(HeadQuarterRepository headQuarterRepository, NursingHomeRepository nursingHomeRepository) {
        this.headQuarterRepository = headQuarterRepository;
        this.nursingHomeRepository = nursingHomeRepository;
    }
    /**
     * Handles the creation of a new headquarter.
     * <p>
     * This method processes a {@link CreateHeadQuarterCommand} by performing the following steps:
     * <ol>
     *   <li>Validates that the nursing home specified in the command exists</li>
     *   <li>Creates an Address value object from the command data</li>
     *   <li>Constructs a new Headquarter entity</li>
     *   <li>Persists the headquarter to the database</li>
     *   <li>Returns the ID of the newly created headquarter</li>
     * </ol>
     * </p>
     *
     * @param command the command containing the nursing home ID, title, and address
     *                for the new headquarter
     * @return the unique identifier of the newly created headquarter
     * @throws IllegalArgumentException if the nursing home specified in the command
     *                                  does not exist, or if there is an error during
     *                                  the save operation
     */
    @Override
    public Long handle(CreateHeadQuarterCommand command) {
       var nursingHomeId= nursingHomeRepository.findById(command.nursingHomeId());
       if (nursingHomeId.isEmpty()){
           throw new IllegalArgumentException("nursingHome not found");
       }
       var nursingHome= nursingHomeId.get();
       var address= new Address(command.address());
       var headQuarter= new Headquarter(nursingHome,command.title(),address);
       try {
           headQuarterRepository.save(headQuarter);
       }catch (Exception e){
              throw new IllegalArgumentException("Error saving headQuarter: %s".formatted(e.getMessage()));
       }
       return headQuarter.getId();
    }
}
