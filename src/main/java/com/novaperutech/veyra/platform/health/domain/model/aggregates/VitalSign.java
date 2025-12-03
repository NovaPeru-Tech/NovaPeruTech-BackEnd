package com.novaperutech.veyra.platform.health.domain.model.aggregates;
import com.novaperutech.veyra.platform.health.domain.model.valueobjects.MeasurementId;
import com.novaperutech.veyra.platform.health.domain.model.valueobjects.SeverityLevel;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.novaperutech.veyra.platform.health.domain.model.valueobjects.ResidentId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
@Entity
@Getter
public class VitalSign extends AuditableAbstractAggregateRoot<VitalSign> {
private ResidentId residentId;
    @Enumerated(EnumType.STRING)
private SeverityLevel severityLevel;
    private MeasurementId measurementId;
public VitalSign(){}
    public VitalSign(ResidentId residentId, SeverityLevel severityLevel, MeasurementId measurementId) {
    this.residentId = residentId;
    this.severityLevel = severityLevel;
    this.measurementId = measurementId;
    }

}
