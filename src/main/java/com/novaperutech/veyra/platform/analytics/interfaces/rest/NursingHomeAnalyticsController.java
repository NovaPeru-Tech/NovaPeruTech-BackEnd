package com.novaperutech.veyra.platform.analytics.interfaces.rest;

import com.novaperutech.veyra.platform.analytics.domain.model.queries.*;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.NursingHomeId;
import com.novaperutech.veyra.platform.analytics.domain.services.MetricQueryService;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.MetricResource;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.transform.MetricResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes analytics endpoints for a nursing home.
 *
 * <p>Responsibility: handle HTTP requests under
 * <code>/api/v1/nursing-homes/{nursingHomeId}/analytics</code> and return
 * analytics as Resource representations.</p>
 *
 * <p>Notes:
 * <ul>
 *   <li>Consumes and produces application/json.</li>
 *   <li>Resources returned are {@link MetricResource} (HTTP representation of Metric aggregates).</li>
 *   <li>Controller is stateless; all stateful operations are delegated to domain services.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/nursing-homes/{nursingHomeId}/analytics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Nursing Homes Analytics")
public class NursingHomeAnalyticsController {

    private final MetricQueryService metricQueryService;

    public NursingHomeAnalyticsController(MetricQueryService metricQueryService) {
        this.metricQueryService = metricQueryService;
    }

    /**
     * Returns aggregated resident admissions analytics for the requested year.
     *
     * @param nursingHomeId the identifier of the nursing home (path variable)
     * @param year the year for which to retrieve admissions analytics
     * @return 200 OK with a {@link MetricResource} containing labels and values when
     * metrics are present; 404 Not Found when no metrics exist for the query
     */
    @GetMapping("/residents/admissions")
    @Operation(
            summary = "Get resident admissions analytics",
            description = "Returns aggregated analytics for resident admissions ready for charts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getResidentAdmissionsAnalytics(
            @PathVariable Long nursingHomeId,
            @RequestParam int year) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetResidentAdmissionsByNursingHomeIdAndYearQuery(nursingHomeIdVO, year);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/residents/active")
    @Operation(
            summary = "Get active residents analytics",
            description = "Returns analytics for active residents"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getActiveResidentsAnalytics(
            @PathVariable Long nursingHomeId,
            @RequestParam int year) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetResidentActivesByNursingHomeIdAndYearQuery(nursingHomeIdVO, year);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/staff/hires")
    @Operation(
            summary = "Get staff hires analytics",
            description = "Returns analytics for staff hires"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getStaffHiresAnalytics(
            @PathVariable Long nursingHomeId,
            @RequestParam int year) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetStaffHiresByNursingHomeIdAndYearQuery(nursingHomeIdVO, year);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/staff/terminations")
    @Operation(
            summary = "Get staff terminations analytics",
            description = "Returns analytics for staff terminations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getStaffTerminationsAnalytics(
            @PathVariable Long  nursingHomeId,
            @RequestParam int year) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetStaffTerminationsByNursingHomeIdAndYearQuery(nursingHomeIdVO, year);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    /**
     * Returns resident admissions analytics for a specific month.
     *
     * @param nursingHomeId the identifier of the nursing home (path variable)
     * @param year the year for which to retrieve admissions analytics
     * @param month the month for which to retrieve admissions analytics
     * @return 200 OK with a {@link MetricResource} containing labels and values when
     * metrics are present; 404 Not Found when no metrics exist for the query
     */
    @GetMapping("/residents/admissions/by-month")
    @Operation(
            summary = "Get resident admissions by specific month",
            description = "Returns analytics for a specific month"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getResidentAdmissionsByMonth(
            @PathVariable Long nursingHomeId,
            @RequestParam int year,
            @RequestParam int month) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetResidentAdmissionsByNursingHomeIdAndYearAndMonthQuery(
                nursingHomeIdVO, year, month);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    /**
     * Returns resident admissions analytics within a date range.
     *
     * @param nursingHomeId the identifier of the nursing home (path variable)
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return 200 OK with a {@link MetricResource} containing labels and values when
     * metrics are present; 404 Not Found when no metrics exist for the query
     */
    @GetMapping("/residents/admissions/by-date-range")
    @Operation(
            summary = "Get resident admissions by date range",
            description = "Returns analytics within a date range"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getResidentAdmissionsByDateRange(
            @PathVariable Long nursingHomeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetResidentAdmissionsByNursingHomeIdAndDateRangeQuery(
                nursingHomeIdVO, startDate, endDate);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    /**
     * Returns staff hires analytics for a specific month.
     *
     * @param nursingHomeId the identifier of the nursing home (path variable)
     * @param year the year for which to retrieve hires analytics
     * @param month the month for which to retrieve hires analytics
     * @return 200 OK with a {@link MetricResource} containing labels and values when
     * metrics are present; 404 Not Found when no metrics exist for the query
     */
    @GetMapping("/staff/hires/by-month")
    @Operation(
            summary = "Get staff hires by specific month",
            description = "Returns analytics for a specific month"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getStaffHiresByMonth(
            @PathVariable Long nursingHomeId,
            @RequestParam int year,
            @RequestParam int month) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetStaffHiresByNursingHomeIdAndYearAndMonthQuery(
                nursingHomeIdVO, year, month);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }

    /**
     * Returns staff terminations analytics for a specific month.
     *
     * @param nursingHomeId the identifier of the nursing home (path variable)
     * @param year the year for which to retrieve terminations analytics
     * @param month the month for which to retrieve terminations analytics
     * @return 200 OK with a {@link MetricResource} containing labels and values when
     * metrics are present; 404 Not Found when no metrics exist for the query
     */
    @GetMapping("/staff/terminations/by-month")
    @Operation(
            summary = "Get staff terminations by specific month",
            description = "Returns analytics for a specific month"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics returned successfully"),
            @ApiResponse(responseCode = "404", description = "No metrics found")
    })
    public ResponseEntity<MetricResource> getStaffTerminationsByMonth(
            @PathVariable Long nursingHomeId,
            @RequestParam int year,
            @RequestParam int month) {

        var nursingHomeIdVO = new NursingHomeId(nursingHomeId);
        var query = new GetStaffTerminationsByNursingHomeIdAndYearAndMonthQuery(
                nursingHomeIdVO, year, month);
        var metrics = metricQueryService.handle(query);

        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = MetricResourceFromEntityAssembler.toResourceFromEntityList(metrics);
        return ResponseEntity.ok(resource);
    }
}