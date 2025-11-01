package com.novaperutech.veyra.platform.nursingHomes.domain.services;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateNursingHomeCommand;

import java.util.Optional;
/**
 * Service interface for handling nursing home command operations.
 * This interface defines the contract for all write operations related to nursing homes,
 * following the CQRS pattern by separating commands from queries.
 * Implementations of this interface are responsible for executing business logic
 * and coordinating the creation of nursing home entities.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHome
 * @see CreateNursingHomeCommand
 */
public interface NursingHomeCommandService {

    /**
     * Handles the creation of a new nursing home.
     * This method processes the command to create a nursing home entity,
     * applying all necessary business rules and validations before persisting it.
     *
     * @param command the {@link CreateNursingHomeCommand} containing the data to create the nursing home
     * @return an {@link Optional} containing the created {@link NursingHome} if successful, or empty if the operation fails
     */
    Optional<NursingHome>handle(CreateNursingHomeCommand command);
}
