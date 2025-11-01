package com.novaperutech.veyra.platform.nursingHomes.domain.model.queries;
/**
 * Query to retrieve all headquarters associated with a specific nursing home.
 * <p>
 * This record encapsulates the nursing home identifier used to fetch all
 * headquarters that belong to that nursing home. It follows the Query pattern
 * in Domain-Driven Design (DDD) and the CQRS (Command-Query Responsibility
 * Segregation) pattern, representing a read operation intent.
 * </p>
 *
 * @param id the unique identifier of the nursing home whose headquarters
 *           are to be retrieved
 *
 * @author NovaPeru Tech
 * @version 1.0
 */

public record GetAllHeadQuarterByNursingHomeIdQuery(Long id) {

}
