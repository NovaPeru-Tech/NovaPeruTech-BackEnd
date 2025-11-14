package com.novaperutech.veyra.platform.nursing.domain.services;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.AddMedicationAdministrationCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.DeleteResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.UpdateResidentCommand;

import java.util.Optional;

public interface ResidentCommandServices {
    Long handle (CreateResidentCommand command);
    Optional<Resident>handle(UpdateResidentCommand command);
    void handle (DeleteResidentCommand command);
    void handle (AddMedicationAdministrationCommand command);
}
