package com.novaperutech.veyra.platform.analytics.interfaces.rest.resources;

import java.math.BigDecimal; import java.util.List;

public record InventoryStatsResource(
        long totalMedications, BigDecimal totalValue,
        long expiringThisMonth,long expiringNextMonth,
        long lowStockItems,long outOfStockItems,
        List<TopMedicationUsageResource> topMedicationsByUsage) {}
