package com.novaperutech.veyra.platform.nursingHomes.domain.services;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.entities.Headquarter;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllHeadQuarterByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetHeadQuarterByIdQuery;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for handling headquarter-related queries.
 * <p>
 * This service defines the contract for processing query operations related to
 * headquarters in the nursing homes domain. It follows the Command-Query Responsibility
 * Segregation (CQRS) pattern, specifically handling read operations (queries) for
 * the headquarter entity. All methods in this interface are read-only and do not
 * modify the state of the system.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see Headquarter
 * @see GetHeadQuarterByIdQuery
 * @see GetAllHeadQuarterByNursingHomeIdQuery
 */
public interface HeadQuarterQueryService {
    /**
     * Handles the query to retrieve a headquarter by its unique identifier.
     * <p>
     * This method processes a {@link GetHeadQuarterByIdQuery} and attempts to find
     * the corresponding headquarter entity in the system.
     * </p>
     *
     * @param query the query containing the headquarter ID to search for
     * @return an {@link Optional} containing the headquarter if found,
     *         or an empty Optional if no headquarter exists with the given ID
     */
    Optional<Headquarter>handle(GetHeadQuarterByIdQuery query);
    /**
     * Handles the query to retrieve all headquarters associated with a specific nursing home.
     * <p>
     * This method processes a {@link GetAllHeadQuarterByNursingHomeIdQuery} and retrieves
     * all headquarters that belong to the specified nursing home.
     * </p>
     *
     * @param query the query containing the nursing home ID
     * @return a list of all headquarters belonging to the specified nursing home,
     *         or an empty list if no headquarters are found for the given nursing home
     */
    List<Headquarter>handle(GetAllHeadQuarterByNursingHomeIdQuery query);
}
