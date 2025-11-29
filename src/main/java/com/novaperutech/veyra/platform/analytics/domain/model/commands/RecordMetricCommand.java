package com.novaperutech.veyra.platform.analytics.domain.model.commands;

import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.MetricCategory;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.MetricType;

import java.time.LocalDate;

public record RecordMetricCommand(Long nursingHomeId,
                                  MetricType metricType,
                                  MetricCategory metricCategory,
                                  LocalDate eventDate) {
}
