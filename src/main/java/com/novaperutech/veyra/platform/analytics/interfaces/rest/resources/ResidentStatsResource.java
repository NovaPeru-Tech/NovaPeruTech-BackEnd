package com.novaperutech.veyra.platform.analytics.interfaces.rest.resources;

import java.util.Map;

public record ResidentStatsResource(
        long totalResidents, Map<String,Long> byGender, Map<String,Long> byAgeRange,
        double averageAge, long newAdmissionsThisMonth, double averageLengthOfStay,
        Map<String,Long> byMedicalCondition) {}
