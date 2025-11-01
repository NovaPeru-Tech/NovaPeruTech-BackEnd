package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources;
/**
 * Resource DTO for creating a new headquarter.
 * <p>
 * This record encapsulates the data required from a client to create a new
 * headquarters entity. It serves as the request body for the headquarter creation
 * endpoint, containing only the essential information needed (title and address).
 * The nursing home ID is typically provided separately as a path parameter.
 * </p>
 * <p>
 * This resource follows the Data Transfer Object (DTO) pattern and is part of
 * the API contract between the client and the server.
 * </p>
 *
 * @param title   the title or name of the headquarter to be created
 *                (e.g., "Main Branch", "North Location")
 * @param address the physical address of the headquarter as a string
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record CreateHeadQuarterResource( String title, String address) {

}
