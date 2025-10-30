package com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.commands.CreateNursingHomeCommand;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.*;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

/**
 * Represents a nursing home aggregate root entity in the domain model.
 * This entity encapsulates all the essential information about a nursing home facility,
 * including its identification, contact details, and location information.
 * It extends {@link AuditableAbstractAggregateRoot} to include audit trail capabilities.
 * The class follows Domain-Driven Design (DDD) principles as an aggregate root,
 * ensuring consistency and encapsulation of business rules related to nursing homes.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see AuditableAbstractAggregateRoot
 * @see CreateNursingHomeCommand
 */
@Entity
@Getter
public class NursingHome extends AuditableAbstractAggregateRoot<NursingHome> {
    /**
     * The name of the nursing home.
     * This is an embedded value object that encapsulates the nursing home's name
     * along with its validation rules.
     */
    @Embedded
    private Name name;
    /**
     * The RUC  of the nursing home.
     * This is the unique tax identification number used in Peru for legal entities.
     * It is stored as an embedded value object with built-in validation.
     */
    @Embedded
    private Ruc ruc;
    /**
     * The primary contact phone number of the nursing home.
     * This embedded value object contains the phone number along with
     * its validation and formatting logic.
     */
    @Embedded
    private PhoneNumber phoneNumber;

    /**
     * The physical address of the nursing home facility.
     * This embedded value object encapsulates all address-related information
     * including street, city, and postal code details.
     */
    @Embedded
    private Address  address;
    /**
     * A textual description of the nursing home.
     * This embedded value object contains detailed information about the nursing home's
     * facilities, services, and other relevant descriptive information.
     */
    @Embedded
    private Description description;
    /**
     * Default constructor for JPA.
     * This constructor is required by JPA for entity instantiation.
     * It should not be used directly in application code.
     */
      public NursingHome(){}
    /**
     * Creates a new nursing home instance from a creation command.
     * This constructor initializes all the nursing home properties by extracting
     * the data from the provided command and wrapping them in their respective
     * value objects. This approach ensures that all business rules and validations
     * embedded in the value objects are applied during entity creation.
     *
     * @param command the {@link CreateNursingHomeCommand} containing the data
     *                to initialize the nursing home entity
     * @throws IllegalArgumentException if any of the command parameters fail
     *                                  validation in their respective value objects
     */
    public NursingHome(CreateNursingHomeCommand command){
          this.name=new Name(command.name());
          this.ruc=new Ruc(command.ruc());
          this.phoneNumber=new PhoneNumber(command.phoneNumber());
          this.description= new Description(command.description());
        this.address=new Address(command.address());

    }
}
