package com.novaperutech.veyra.platform.analytics.interfaces.rest.transform;
import com.novaperutech.veyra.platform.analytics.domain.model.aggregates.Metric;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.MetricResource;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Assembler that converts a list of {@link Metric} domain aggregates into a
 * {@link MetricResource} HTTP representation.
 *
 * <p>This is a pure mapping utility: it does not modify the domain objects or
 * persist state. It is used by controllers to produce Resource payloads for
 * clients.</p>
 *
 * @see MetricResource
 * @see com.novaperutech.veyra.platform.analytics.domain.model.aggregates.Metric
 */
public class MetricResourceFromEntityAssembler {
    /**
     * Convert a list of metrics into a single {@link MetricResource} suitable for
     * charting clients. When the input is empty an empty resource with zero total
     * is returned.
     *
     * @param metrics list of Metric domain aggregates (may be empty)
     * @return a MetricResource containing labels, values, metricType and total
     */
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