package com.novaperutech.veyra.platform.analytics.domain.services;

import com.novaperutech.veyra.platform.analytics.domain.model.aggregates.Metric;
import com.novaperutech.veyra.platform.analytics.domain.model.queries.*;

import java.util.List;

public interface MetricQueryService {
    List<Metric>handle(GetResidentActivesByNursingHomeIdAndYearQuery query);
    List<Metric>handle(GetResidentAdmissionsByNursingHomeIdAndDateRangeQuery query);
    List<Metric>handle(GetResidentAdmissionsByNursingHomeIdAndYearAndMonthQuery query);
    List<Metric>handle(GetResidentAdmissionsByNursingHomeIdAndYearQuery query);
    List<Metric>handle(GetStaffTerminationsByNursingHomeIdAndYearQuery query);
    List<Metric>handle(GetStaffTerminationsByNursingHomeIdAndYearAndMonthQuery query);
    List<Metric>handle(GetStaffHiresByNursingHomeIdAndYearQuery query);
    List<Metric>handle(GetStaffHiresByNursingHomeIdAndYearAndMonthQuery query);

}
