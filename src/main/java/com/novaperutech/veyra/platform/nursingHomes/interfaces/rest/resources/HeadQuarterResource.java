package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources;

/**
 * Resource DTO representing a headquarter entity.
 * <p>
 * This record encapsulates the data of a headquarter that is returned to clients
 * in API responses. It serves as the response body for headquarter-related endpoints,
 * containing all relevant information about a headquarter including its unique identifier,
 * associated nursing home, title, and address.
 * </p>
 * <p>
 * This resource follows the Data Transfer Object (DTO) pattern and is part of
 * the API contract between the server and the client.
 * </p>
 *
 * @param id            the unique identifier of the headquarter
 * @param nursingHomeId the unique identifier of the nursing home to which
 *                      this headquarter belongs
 * @param title         the title or name of the headquarter
 *                      (e.g., "Main Branch", "North Location")
 * @param address       the physical address of the headquarter as a string
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record HeadQuarterResource(Long id,Long nursingHomeId,String title,String address) {

}
