package com.novaperutech.veyra.platform.tracking.domain.model.aggregates;

import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.novaperutech.veyra.platform.tracking.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Measurement extends AuditableAbstractAggregateRoot<Measurement> {

    @Embedded
    private ResidentId residentId;

    @Embedded
    private DeviceId deviceId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "heart_rate"))
    private HeartRate heartRate;

    @Embedded
    private BloodPressure bloodPressure;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "temperature"))
    private Temperature temperature;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "oxygen_saturation"))
    private OxygenSaturation oxygenSaturation;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "respiratory_rate"))
    private RespiratoryRate respiratoryRate;



    protected Measurement() {
    }

    public Measurement(
            ResidentId residentId,
            DeviceId deviceId,
            HeartRate heartRate,
            BloodPressure bloodPressure,
            OxygenSaturation oxygenSaturation,
            RespiratoryRate respiratoryRate) {

        this.residentId = residentId;
        this.deviceId = deviceId;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
    }



}