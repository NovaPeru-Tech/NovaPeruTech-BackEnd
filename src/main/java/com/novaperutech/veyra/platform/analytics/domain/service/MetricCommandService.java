package com.novaperutech.veyra.platform.analytics.domain.service;

import com.novaperutech.veyra.platform.analytics.domain.model.commands.RecordMetricCommand;

public interface MetricCommandService {
    void handle(RecordMetricCommand command);
}
