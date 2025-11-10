package com.novaperutech.veyra.platform.analytics.interfaces.rest.resources;

public record GeneralStatsResource(
        long totalResidents,long activeResidents,
        long totalEmployees,long activeEmployees,
        long totalMedications,long lowStockMedications,
        long upcomingActivities,double occupancyRate,double averageAge) {}
