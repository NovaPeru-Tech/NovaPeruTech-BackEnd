package com.novaperutech.veyra.platform.nursingHomes.application.internal.queryservices;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.entities.Headquarter;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllHeadQuarterByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetHeadQuarterByIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.HeadQuarterQueryService;
import com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories.HeadQuarterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link HeadQuarterQueryService} for handling headquarter query operations.
 * <p>
 * This service implements the query side of the CQRS pattern, providing read-only
 * operations for retrieving headquarter information. It acts as an intermediary
 * between the domain layer and the infrastructure layer, delegating data retrieval
 * operations to the {@link HeadQuarterRepository}.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see HeadQuarterQueryService
 * @see HeadQuarterRepository
 * @see Headquarter
 */
@Service
public class HeadQuarterQueryServiceImpl implements HeadQuarterQueryService {
    /**
     * Repository for accessing headquarter data from the database.
     */
private final HeadQuarterRepository headQuarterRepository;
    /**
     * Constructs a new HeadQuarterQueryServiceImpl with the required repository.
     *
     * @param headQuarterRepository the repository for headquarter data access,
     *                              injected by Spring's dependency injection
     */
    public HeadQuarterQueryServiceImpl(HeadQuarterRepository headQuarterRepository) {
        this.headQuarterRepository = headQuarterRepository;
    }
    /**
     * Handles the query to retrieve a headquarter by its unique identifier.
     * <p>
     * This method processes a {@link GetHeadQuarterByIdQuery} and attempts to
     * find the corresponding headquarter entity in the database.
     * </p>
     *
     * @param query the query containing the headquarter ID to search for
     * @return an {@link Optional} containing the headquarter if found,
     *         or an empty Optional if no headquarter exists with the given ID
     */
    @Override
    public Optional<Headquarter> handle(GetHeadQuarterByIdQuery query) {
        return headQuarterRepository.findById(query.id());
    }
    /**
     * Handles the query to retrieve all headquarters for a specific nursing home.
     * <p>
     * This method processes a {@link GetAllHeadQuarterByNursingHomeIdQuery} and
     * retrieves all headquarters associated with the specified nursing home.
     * </p>
     *
     * @param query the query containing the nursing home ID
     * @return a list of all headquarters belonging to the specified nursing home,
     *         or an empty list if no headquarters are found
     */

    @Override
    public List<Headquarter> handle(GetAllHeadQuarterByNursingHomeIdQuery query) {
        return headQuarterRepository.findAllByNursingHomeId(query.id());
    }
}
