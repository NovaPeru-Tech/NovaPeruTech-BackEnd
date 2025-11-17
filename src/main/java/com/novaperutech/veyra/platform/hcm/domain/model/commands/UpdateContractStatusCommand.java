package com.novaperutech.veyra.platform.hcm.domain.model.commands;

import java.time.LocalDate;

public record UpdateContractStatusCommand(Long staffMemberId,
                                          Long contractId, String newStatus) {
}
