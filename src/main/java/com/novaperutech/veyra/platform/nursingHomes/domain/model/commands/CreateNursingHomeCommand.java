package com.novaperutech.veyra.platform.nursingHomes.domain.model.commands;
/**
 * Command for creating a new nursing home.
 * This record encapsulates all the necessary data required to create a nursing home entity.
 * It follows the Command pattern in CQRS architecture, representing an intention to
 * perform a write operation in the system.
 *
 * @param name the name of the nursing home
 * @param ruc the RUC  tax identification number
 * @param phoneNumber the primary contact phone number
 * @param address the physical address of the nursing home facility
 * @param description a textual description of the nursing home and its services
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
public record CreateNursingHomeCommand(String name,String ruc,String phoneNumber,String address, String description) {
}
