package com.novaperutech.veyra.platform.analytics.domain.model.aggregates;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.MetricCategory;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.MetricType;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.NursingHomeId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Metric extends AuditableAbstractAggregateRoot<Metric> {
    @Embedded
    private NursingHomeId nursingHomeId;
    @Enumerated(EnumType.STRING)
    private MetricType metricType;
    @Enumerated(EnumType.STRING)
    private MetricCategory metricCategory;
    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private Long value;

    public Metric() {}
    public Metric(NursingHomeId nursingHomeId, MetricType metricType,
                  MetricCategory metricCategory,LocalDate eventDate) {
        this.nursingHomeId = nursingHomeId;
        this.metricType = metricType;
        this.metricCategory = metricCategory;
        this.value = 1L;
        this.eventDate=eventDate;
    }
    public void incrementValue() {
        this.value++;
    }
}
