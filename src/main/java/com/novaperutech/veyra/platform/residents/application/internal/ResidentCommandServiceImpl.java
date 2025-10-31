package com.novaperutech.veyra.platform.residents.application.internal;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.DeleteResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.services.ResidentCommandService;
import com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResidentCommandServiceImpl implements ResidentCommandService {

    private final ResidentRepository residentRepository;

    public ResidentCommandServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public Resident handle(CreateResidentCommand command) {
        Resident resident = new Resident(command);
        return residentRepository.save(resident);
    }

    @Override
    public Resident handle(UpdateResidentCommand command) {
        return residentRepository.findById(command.id())
                .map(existingResident -> {
                    existingResident.update(command);
                    return residentRepository.save(existingResident);
                })
                .orElse(null);
    }

    @Override
    public void handle(DeleteResidentCommand command) {
        residentRepository.deleteById(command.id());
    }
}
