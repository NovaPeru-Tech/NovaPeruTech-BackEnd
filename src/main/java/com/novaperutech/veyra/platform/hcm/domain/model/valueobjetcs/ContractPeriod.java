package com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
@Embeddable
public record ContractPeriod(LocalDate startDate, LocalDate endDate) {

}
