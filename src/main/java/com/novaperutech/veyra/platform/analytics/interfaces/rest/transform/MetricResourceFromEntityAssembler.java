package com.novaperutech.veyra.platform.analytics.interfaces.rest.transform;
import com.novaperutech.veyra.platform.analytics.domain.model.aggregates.Metric;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.MetricResource;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
public class MetricResourceFromEntityAssembler {
    public static MetricResource toResourceFromEntityList(List<Metric> metrics) {
        if (metrics.isEmpty()) {
            return new MetricResource(Collections.emptyList(), Collections.emptyList(), "", 0L);
        }
        var labels = metrics.stream()
                .map(metric -> metric.getEventDate().getMonth()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .toList();
        var values = metrics.stream()
                .map(Metric::getValue)
                .toList();
        var total = values.stream()
                .mapToLong(Long::longValue)
                .sum();
        var metricType = metrics.getFirst().getMetricType().name();
        return new MetricResource(labels, values, metricType, total);
    }
}