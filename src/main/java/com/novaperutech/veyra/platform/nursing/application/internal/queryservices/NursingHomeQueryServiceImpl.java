package com.novaperutech.veyra.platform.nursing.application.internal.queryservices;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetNursingHomeByIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeQueryServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.NursingHomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NursingHomeQueryServiceImpl implements NursingHomeQueryServices {
private final NursingHomeRepository nursingHomeRepository;

    public NursingHomeQueryServiceImpl(NursingHomeRepository nursingHomeRepository) {
        this.nursingHomeRepository = nursingHomeRepository;

    }

    @Override
    public Optional<NursingHome> handle(GetNursingHomeByIdQuery query) {
        return nursingHomeRepository.findById(query.id());
    }

    @Override
    public List<NursingHome> handle(GetAllNursingHomeQuery query) {
        return nursingHomeRepository.findAll();
    }
}
