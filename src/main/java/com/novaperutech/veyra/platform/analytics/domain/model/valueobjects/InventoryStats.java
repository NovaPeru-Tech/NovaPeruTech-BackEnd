package com.novaperutech.veyra.platform.analytics.domain.model.valueobjects;

import java.math.BigDecimal; import java.util.List;

public record InventoryStats(
        long totalMedications, BigDecimal totalValue,
        long expiringThisMonth,long expiringNextMonth,
        long lowStockItems,long outOfStockItems,
        List<TopMedicationUsage> topMedicationsByUsage) {}
