package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing an administrator identifier.
 * <p>
 * This immutable record encapsulates the unique identifier of an administrator
 * within the nursing homes domain. As an {@link Embeddable} value object, it can
 * be embedded into entity classes to represent a reference to an administrator
 * without establishing a direct entity relationship.
 * </p>
 * <p>
 * This class follows the Value Object pattern from Domain-Driven Design (DDD),
 * where it represents a descriptive aspect of the domain with no conceptual identity.
 * Being a record, it is inherently immutable and provides automatic implementation
 * of equals, hashCode, and toString methods based on its content.
 * </p>
 *
 * @param administratorId the unique identifier of the administrator
 *
 * @author NovaPeru Tech
 * @version 1.0
 */
@Embeddable
public record AdministratorId(Long administratorId) {
}
