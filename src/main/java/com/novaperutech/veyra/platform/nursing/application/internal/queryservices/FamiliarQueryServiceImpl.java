package com.novaperutech.veyra.platform.nursing.application.internal.queryservices;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Familiar;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetFamiliarByIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.FamiliarQueryService;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.FamiliarRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamiliarQueryServiceImpl implements FamiliarQueryService {
    private final FamiliarRepository familiarRepository;

    public FamiliarQueryServiceImpl(FamiliarRepository familiarRepository) {
        this.familiarRepository = familiarRepository;
    }

    @Override
    public Optional<Familiar> handle(GetFamiliarByIdQuery query) {
        return familiarRepository.findById(query.id());
    }
}
