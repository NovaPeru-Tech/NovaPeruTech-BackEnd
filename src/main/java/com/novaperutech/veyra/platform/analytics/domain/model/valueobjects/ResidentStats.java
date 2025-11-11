package com.novaperutech.veyra.platform.analytics.domain.model.valueobjects;

import java.util.Map;

public record ResidentStats(
        long totalResidents, Map<String,Long> byGender, Map<String,Long> byAgeRange,
        double averageAge, long newAdmissionsThisMonth, double averageLengthOfStay,
        Map<String,Long> byMedicalCondition) {}
