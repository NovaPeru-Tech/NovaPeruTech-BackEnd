package com.novaperutech.veyra.platform.nursing.domain.services;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllResidentsQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetResidentByPersonProfileQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetResidentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ResidentQueryServices {
    List<Resident>handle(GetAllResidentsQuery query);
    Optional<Resident>handle(GetResidentByIdQuery query);
    Optional<Resident>handle(GetResidentByPersonProfileQuery query);
}
