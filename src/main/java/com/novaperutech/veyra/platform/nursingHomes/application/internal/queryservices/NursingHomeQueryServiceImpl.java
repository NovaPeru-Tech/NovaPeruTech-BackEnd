package com.novaperutech.veyra.platform.nursingHomes.application.internal.queryservices;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetNursingHomeByIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.NursingHomeQueryService;
import com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the nursing home query service.
 * This service handles all read operations related to nursing home entities.
 * It provides methods to retrieve nursing homes from the repository without
 * modifying the data, following the CQRS pattern.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHomeQueryService
 * @see NursingHome
 */
@Service
public class NursingHomeQueryServiceImpl implements NursingHomeQueryService {
    /**
     * Repository for nursing home data retrieval operations.
     */
    private final NursingHomeRepository nursingHomeRepository;
    /**
     * Constructs a new NursingHomeQueryServiceImpl with the required repository.
     *
     * @param nursingHomeRepository the repository to retrieve nursing home data
     */
    public NursingHomeQueryServiceImpl(NursingHomeRepository nursingHomeRepository) {
        this.nursingHomeRepository = nursingHomeRepository;
    }
    /**
     * Handles the retrieval of all nursing homes.
     * This method fetches all nursing home entities from the repository
     * without any filtering or pagination.
     *
     * @param query the {@link GetAllNursingHomeQuery} representing the request
     * @return a {@link List} of all {@link NursingHome} entities in the system
     */
    @Override
    public List<NursingHome> handle(GetAllNursingHomeQuery query) {
        return  nursingHomeRepository.findAll();
    }
    /**
     * Handles the retrieval of a nursing home by its unique identifier.
     * This method attempts to find a nursing home with the specified ID.
     *
     * @param query the {@link GetNursingHomeByIdQuery} containing the nursing home ID
     * @return an {@link Optional} containing the {@link NursingHome} if found, or empty if not found
     */
    @Override
    public Optional<NursingHome> handle(GetNursingHomeByIdQuery query) {
        return nursingHomeRepository.findById(query.id());
    }
}
