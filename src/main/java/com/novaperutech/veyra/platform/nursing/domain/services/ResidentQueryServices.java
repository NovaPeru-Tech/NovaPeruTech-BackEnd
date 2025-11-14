package com.novaperutech.veyra.platform.nursing.domain.services;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.entities.MedicationAdministration;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ResidentQueryServices {
    List<Resident>handle(GetAllResidentsByNursingHomeIdQuery query);
    Optional<Resident>handle(GetResidentByIdQuery query);
    Optional<Resident>handle(GetResidentByPersonProfileQuery query);
    List<Resident>handle(GetActiveResidentsByNursingHomeId query);
    List<MedicationAdministration>handle(GetMedicationAdministrationsByResidentIdQuery query);
}
