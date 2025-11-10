package com.novaperutech.veyra.platform.hcm.domain.services;

import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.commands.*;

import java.util.Optional;

public interface StaffCommandServices {
    Long handle(CreateStaffCommand command);
    Optional<Staff>handle(UpdateStaffCommand command);
    void handle(DeleteStaffCommand command);
    Long handle(AddContractToStaffCommand command);
    void handle(UpdateContractStatusCommand command);
}
