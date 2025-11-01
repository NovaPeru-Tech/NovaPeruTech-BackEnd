package com.novaperutech.veyra.platform.nursingHomes.domain.model.commands;
/**
 * Command to create a new headquarters for a nursing home.
 * <p>
 * This record encapsulates all the necessary information required to create
 * a new headquarters entity, including the nursing home identifier, the
 * headquarters title, and its address.
 * </p>
 * <p>
 * This command follows the Command pattern in Domain-Driven Design (DDD),
 * representing an intention to perform a state-changing operation in the domain.
 * </p>
 *
 * @param nursingHomeId the unique identifier of the nursing home to which
 *                      this headquarter will belong
 * @param title         the title or name of the headquarters (e.g., "Main Branch",
 *                      "North Location")
 * @param address       the physical address of the headquarters as a string
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record CreateHeadQuarterCommand(Long nursingHomeId, String title, String address) {
}
