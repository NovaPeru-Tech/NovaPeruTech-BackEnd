package com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates;

import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Administrator extends AuditableAbstractAggregateRoot<Administrator> {

}
