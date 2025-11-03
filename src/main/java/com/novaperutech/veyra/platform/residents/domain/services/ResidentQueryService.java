package com.novaperutech.veyra.platform.residents.domain.services;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.domain.model.queries.GetAllResidentsQuery;
import com.novaperutech.veyra.platform.residents.domain.model.queries.GetResidentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ResidentQueryService {

    List<Resident> handle(GetAllResidentsQuery query);
    Optional<Resident> handle(GetResidentByIdQuery query);

}
