/**
 * Aggregate root representing a nursing home within the system.
 *
 * <p>This entity links to a business profile through its {@link BusinessProfileId}
 * value object, and inherits auditing and domain event capabilities from
 * {@link AuditableAbstractAggregateRoot}.</p>
 *
 * @summary Represents a nursing home aggregate with an embedded business profile identifier.
 */
package com.novaperutech.veyra.platform.nursing.domain.model.aggregates;

import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.BusinessProfileId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class NursingHome extends AuditableAbstractAggregateRoot<NursingHome> {

    @Embedded
    private BusinessProfileId businessProfileId;

    public NursingHome() {}

    public NursingHome(BusinessProfileId businessProfileId) {
        this.businessProfileId = businessProfileId;
    }
}
