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
    public NursingHome(){}
    public NursingHome(BusinessProfileId businessProfileId) {
        this.businessProfileId = businessProfileId;
    }
}
