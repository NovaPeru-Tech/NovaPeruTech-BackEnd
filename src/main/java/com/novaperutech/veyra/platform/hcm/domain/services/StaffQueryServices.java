package com.novaperutech.veyra.platform.hcm.domain.services;

import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.entities.Contract;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface StaffQueryServices {
    Optional<Staff>handle(GetStaffByIdQuery query);
    List<Staff>handle(GetAllStaffQuery query);
    Optional<Contract>handle(GetActiveContractByStaffMemberId query);
    List<Contract>handle(GetAllContractsByStaffMemberId query);
    Optional<Contract>handle(GetContractByStaffMemberIdAndContractId query);
}
