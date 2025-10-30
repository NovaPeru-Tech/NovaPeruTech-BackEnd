package com.novaperutech.veyra.platform.nursingHomes.domain.services;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetNursingHomeByIdQuery;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for handling nursing home query operations.
 * This interface defines the contract for all read operations related to nursing homes,
 * following the CQRS pattern by separating queries from commands.
 * Implementations of this interface are responsible for retrieving nursing home data
 * without modifying it.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHome
 * @see GetAllNursingHomeQuery
 * @see GetNursingHomeByIdQuery
 */
public interface NursingHomeQueryService {
    /**
     * Handles the retrieval of all nursing homes.
     * This method processes the query to fetch all nursing home entities
     * from the data source.
     *
     * @param query the {@link GetAllNursingHomeQuery} representing the request to retrieve all nursing homes
     * @return a {@link List} containing all {@link NursingHome} entities
     */
    List<NursingHome>handle(GetAllNursingHomeQuery query);
    /**
     * Handles the retrieval of a nursing home by its unique identifier.
     * This method processes the query to fetch a specific nursing home entity
     * based on the provided ID.
     *
     * @param query the {@link GetNursingHomeByIdQuery} containing the nursing home ID
     * @return an {@link Optional} containing the {@link NursingHome} if found, or empty if not found
     */
    Optional<NursingHome>handle (GetNursingHomeByIdQuery query);
}
