package com.novaperutech.veyra.platform.residents.domain.services;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.DeleteResidentCommand;

public interface ResidentCommandService {

    Long handle(CreateResidentCommand command);
    Resident handle(UpdateResidentCommand command);
    void handle(DeleteResidentCommand command);
}
