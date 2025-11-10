package com.novaperutech.veyra.platform.analytics.domain.model.valueobjects;

public record GeneralStats(
        long totalResidents,long activeResidents,
        long totalEmployees,long activeEmployees,
        long totalMedications,long lowStockMedications,
        long upcomingActivities,double occupancyRate,double averageAge) {}
