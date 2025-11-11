package com.novaperutech.veyra.platform.analytics.interfaces.rest;

import com.novaperutech.veyra.platform.analytics.application.internal.queryservices.AnalyticsQueryServices;
import com.novaperutech.veyra.platform.analytics.domain.model.queries.GetGeneralAnalyticsQuery;
import com.novaperutech.veyra.platform.analytics.domain.model.queries.GetInventoryAnalyticsQuery;
import com.novaperutech.veyra.platform.analytics.domain.model.queries.GetResidentStatisticsQuery;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.GeneralStatsResource;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.InventoryStatsResource;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.ResidentStatsResource;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.transform.AnalyticsResourceAssembler;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsQueryServices service;

    public AnalyticsController(AnalyticsQueryServices service) {
        this.service = service;
    }

    // TS-ST001: estadísticas generales
    @GetMapping("/{nursingHomeId}")
    public GeneralStatsResource getGeneral(@PathVariable Long nursingHomeId) {
        var vo = service.handle(new GetGeneralAnalyticsQuery(nursingHomeId));
        return AnalyticsResourceAssembler.toResource(vo);
    }

    // TS-ST002: inventario
    @GetMapping("/{nursingHomeId}/inventory")
    public InventoryStatsResource getInventory(@PathVariable Long nursingHomeId) {
        var vo = service.handle(new GetInventoryAnalyticsQuery(nursingHomeId));
        return AnalyticsResourceAssembler.toResource(vo);
    }

    // TS-ST003: residentes
    @GetMapping("/{nursingHomeId}/statistics/residents")
    public ResidentStatsResource getResidents(@PathVariable Long nursingHomeId) {
        var vo = service.handle(new GetResidentStatisticsQuery(nursingHomeId));
        return AnalyticsResourceAssembler.toResource(vo);
    }
}
