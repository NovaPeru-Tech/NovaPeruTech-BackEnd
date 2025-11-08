package com.novaperutech.veyra.platform.hcm.domain.services;

import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetAllStaffQuery;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetStaffByIdQuery;

import java.util.List;
import java.util.Optional;

public interface StaffQueryServices {
    Optional<Staff>handle(GetStaffByIdQuery query);
    List<Staff>handle(GetAllStaffQuery query);
}
