package com.novaperutech.veyra.platform.nursingHomes.domain.services;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateHeadQuarterCommand;
/**
 * Service interface for handling headquarter-related commands.
 * <p>
 * This service defines the contract for processing command operations related
 * to headquarters in the nursing homes domain. It follows the Command-Query
 * Responsibility Segregation (CQRS) pattern, specifically handling write
 * operations (commands) for the headquarter entity.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see CreateHeadQuarterCommand
 */
public interface HeadQuarterCommandService {
    /**
     * Handles the creation of a new headquarter.
     * <p>
     * Processes the {@link CreateHeadQuarterCommand} to create a new headquarter
     * entity in the system. This method validates the command data, creates the
     * headquarter, and persists it to the database.
     * </p>
     *
     * @param command the command containing all necessary information to create
     *                a new headquarter (nursing home ID, title, and address)
     * @return the unique identifier (ID) of the newly created headquarter
     * @throws IllegalArgumentException if the command contains invalid data
     * @throws jakarta.persistence.EntityNotFoundException if the nursing home
     *         specified in the command does not exist
     */
Long handle (CreateHeadQuarterCommand command);

}
