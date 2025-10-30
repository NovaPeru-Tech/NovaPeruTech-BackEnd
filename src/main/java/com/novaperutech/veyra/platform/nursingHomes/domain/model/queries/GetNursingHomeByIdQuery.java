package com.novaperutech.veyra.platform.nursingHomes.domain.model.queries;
/**
 * Query for retrieving a nursing home by its unique identifier.
 * This record encapsulates the parameters needed to fetch a specific nursing home entity.
 * It follows the Query pattern in CQRS architecture, representing an intention to
 * perform a read operation in the system.
 *
 * @param id the unique identifier of the nursing home to retrieve
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record GetNursingHomeByIdQuery(Long id) {

}
