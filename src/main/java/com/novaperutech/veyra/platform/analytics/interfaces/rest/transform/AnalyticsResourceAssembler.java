package com.novaperutech.veyra.platform.analytics.interfaces.rest.transform;

import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.*;
import com.novaperutech.veyra.platform.analytics.interfaces.rest.resources.*;

public final class AnalyticsResourceAssembler {

    public static GeneralStatsResource toResource(GeneralStats s){
        return new GeneralStatsResource(
                s.totalResidents(), s.activeResidents(),
                s.totalEmployees(), s.activeEmployees(),
                s.totalMedications(), s.lowStockMedications(),
                s.upcomingActivities(), s.occupancyRate(), s.averageAge());
    }

    public static InventoryStatsResource toResource(InventoryStats s){
        var top = s.topMedicationsByUsage().stream()
                .map(t -> new TopMedicationUsageResource(t.medicationId(), t.name(), t.uses()))
                .toList();
        return new InventoryStatsResource(
                s.totalMedications(), s.totalValue(),
                s.expiringThisMonth(), s.expiringNextMonth(),
                s.lowStockItems(), s.outOfStockItems(), top);
    }

    public static ResidentStatsResource toResource(ResidentStats s){
        return new ResidentStatsResource(
                s.totalResidents(), s.byGender(), s.byAgeRange(),
                s.averageAge(), s.newAdmissionsThisMonth(), s.averageLengthOfStay(),
                s.byMedicalCondition());
    }
}
