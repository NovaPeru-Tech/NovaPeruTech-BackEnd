package com.novaperutech.veyra.platform.nursingHomes.domain.model.queries;

/**
 * Query to retrieve a specific headquarter by its unique identifier.
 * <p>
 * This record encapsulates the headquarter identifier used to fetch a single
 * headquarter entity from the system. It follows the Query pattern in
 * Domain-Driven Design (DDD) and the CQRS (Command-Query Responsibility
 * Segregation) pattern, representing a read operation intent.
 * </p>
 *
 * @param id the unique identifier of the headquarter to be retrieved
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record GetHeadQuarterByIdQuery(Long id) {
}
