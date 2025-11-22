package com.novaperutech.veyra.platform.analytics.interfaces.rest.resources;

import java.util.List;

public record MetricResource( List<String> labels,
                              List<Long> values,
                              String metricType,
                              Long total) {
}
