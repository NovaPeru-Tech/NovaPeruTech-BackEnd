package com.novaperutech.veyra.platform.residents.application.internal;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.domain.model.services.ResidentQueryService;
import com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.ResidentRepository;
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
    public List<Resident> handle() {
        return residentRepository.findAll();
    }

    @Override
    public Optional<Resident> handle(Long id) {
        return residentRepository.findById(id);
    }

    @Override
    public List<Resident> handle(String state) {
        return residentRepository.findByState(state);
    }
}
