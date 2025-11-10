package com.novaperutech.veyra.platform.hcm.domain.model.commands;

import java.time.LocalDate;

public record AddContractToStaffCommand(Long staffId, LocalDate startDate, LocalDate endDate, String typeOfContract,
                                        String staffRole, String workShift) {
}
