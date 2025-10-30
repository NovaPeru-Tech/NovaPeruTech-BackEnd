package com.novaperutech.veyra.platform.nursingHomes.domain.model.queries;
/**
 * Query for retrieving all nursing homes.
 * This record represents a query to fetch all nursing home entities from the system.
 * It follows the Query pattern in CQRS architecture, representing an intention to
 * perform a read operation without any filtering criteria.
 * This query does not require any parameters as it retrieves all available nursing homes.
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record GetAllNursingHomeQuery() {
}
