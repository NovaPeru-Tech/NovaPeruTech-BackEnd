// AnalyticsQueryServiceImpl.java
package com.novaperutech.veyra.platform.analytics.application.internal.queryservices;

import com.novaperutech.veyra.platform.analytics.domain.model.queries.*;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.*;
import com.novaperutech.veyra.platform.analytics.infrastructure.persistence.jpa.repositories.AnalyticsReadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsQueryServiceImpl implements AnalyticsQueryServices {

    private final AnalyticsReadRepository repo;
    public AnalyticsQueryServiceImpl(AnalyticsReadRepository repo){ this.repo = repo; }

    @Override
    public GeneralStats handle(GetGeneralAnalyticsQuery query) {
        Long nh = query.nursingHomeId();
        long totalResidents   = repo.countResidents(nh);
        long activeResidents  = repo.countActiveResidents(nh);
        long totalEmployees   = repo.countEmployees(nh);
        long activeEmployees  = repo.countActiveEmployees(nh);
        long totalMedications = repo.countMedications(nh);
        long lowStock         = repo.countLowStock(nh);
        long upcomingActs     = repo.countUpcomingActivities(nh, LocalDate.now());
        long beds             = repo.countBeds(nh);
        long occupiedBeds     = repo.countOccupiedBeds(nh);
        double occupancy      = beds == 0 ? 0.0 : (occupiedBeds * 100.0 / beds);
        double avgAge         = repo.averageResidentAge(nh);

        return new GeneralStats(totalResidents,activeResidents,totalEmployees,activeEmployees,
                totalMedications,lowStock,upcomingActs,occupancy,avgAge);
    }

    @Override
    public InventoryStats handle(GetInventoryAnalyticsQuery query) {
        Long nh = query.nursingHomeId();
        var now = LocalDate.now();
        var s   = now.withDayOfMonth(1);
        var e   = now.withDayOfMonth(now.lengthOfMonth());
        var s2  = s.plusMonths(1);
        var e2  = s2.withDayOfMonth(s2.lengthOfMonth());

        List<TopMedicationUsage> top = repo.topMedicationsByUsage(nh).stream()
                .map(r -> new TopMedicationUsage(((Number)r[0]).longValue(), (String)r[1], ((Number)r[2]).longValue()))
                .toList();

        return new InventoryStats(
                repo.countMedications(nh),
                repo.sumInventoryValue(nh),
                repo.countExpiringBetween(nh, s, e),
                repo.countExpiringBetween(nh, s2, e2),
                repo.countLowStock(nh),
                repo.countOutOfStock(nh),
                top
        );
    }

    @Override
    public ResidentStats handle(GetResidentStatisticsQuery query) {
        Long nh = query.nursingHomeId();
        var now = LocalDate.now();
        var s   = now.withDayOfMonth(1);
        var e   = now.withDayOfMonth(now.lengthOfMonth());

        Map<String, Long> byGender = toMap(repo.residentsByGender(nh));
        Map<String, Long> byAge    = new LinkedHashMap<>(toMap(repo.residentsByAgeBucket(nh)));
        byAge.putIfAbsent("0-17",0L); byAge.putIfAbsent("18-39",0L);
        byAge.putIfAbsent("40-64",0L); byAge.putIfAbsent("65-79",0L);
        byAge.putIfAbsent("80+",0L);

        return new ResidentStats(
                repo.countResidents(nh),
                byGender,
                byAge,
                repo.averageResidentAge(nh),
                repo.countAdmissionsInMonth(nh, s, e),
                repo.averageLengthOfStayDays(nh),
                toMap(repo.residentsByCondition(nh))
        );
    }

    private Map<String,Long> toMap(List<Object[]> rows){
        Map<String,Long> m=new LinkedHashMap<>();
        for(Object[] r: rows) m.put(String.valueOf(r[0]), ((Number)r[1]).longValue());
        return m;
    }
}
