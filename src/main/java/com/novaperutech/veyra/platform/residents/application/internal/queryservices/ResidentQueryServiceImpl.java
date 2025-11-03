package com.novaperutech.veyra.platform.residents.application.internal.queryservices;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.domain.model.queries.GetAllResidentsQuery;
import com.novaperutech.veyra.platform.residents.domain.model.queries.GetResidentByIdQuery;
import com.novaperutech.veyra.platform.residents.domain.services.ResidentQueryService;
import com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResidentQueryServiceImpl implements ResidentQueryService {

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
        return residentRepository.findById(query.Id());
    }
}
