package com.novaperutech.veyra.platform.nursing.domain.services;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetNursingHomeByIdQuery;

import java.util.List;
import java.util.Optional;

public interface NursingHomeQueryServices {
    Optional<NursingHome>handle(GetNursingHomeByIdQuery query);
    List<NursingHome>handle(GetAllNursingHomeQuery query);
}
