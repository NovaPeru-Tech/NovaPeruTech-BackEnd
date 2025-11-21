package com.novaperutech.veyra.platform.analytics.domain.model.queries;

import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.NursingHomeId;

public record GetStaffHiresByNursingHomeIdAndYearAndMonthQuery(NursingHomeId nursingHomeId,Integer year,Integer moth) {
    public GetStaffHiresByNursingHomeIdAndYearAndMonthQuery{
        if (year==null){
            throw new IllegalArgumentException("year must not be null");
        }
        if (year<1900||year>2025)
        {
            throw new IllegalArgumentException("year must be between 1900 and 2025");
        }
        if (moth==null){
            throw new IllegalArgumentException("moth must not be null");
        }
        if (moth<1||moth>12)
        {
            throw new IllegalArgumentException("moth must be between 1 and 12");
        }
    }
}
