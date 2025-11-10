package com.novaperutech.veyra.platform.nursing.application.internal.queryservices;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllResidentsQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetResidentByPersonProfileQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetResidentByIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentQueryServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ResidentQueryServiceImpl implements ResidentQueryServices {
    private final ResidentRepository residentRepository;

    public ResidentQueryServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public List<Resident> handle(GetAllResidentsQuery query) {
        return residentRepository.findAll();
    }

    @Override
    public Optional<Resident> handle(GetResidentByIdQuery query) {
        return residentRepository.findById(query.id());
    }

    @Override
    public Optional<Resident> handle(GetResidentByPersonProfileQuery query) {
        return residentRepository.findByPersonProfileId(query.id());
    }
}
