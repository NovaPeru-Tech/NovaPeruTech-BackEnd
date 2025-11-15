package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.entities.MedicationAdministration;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.MedicationAdministrationResource;

public class MedicationAdministrationResourceFromEntityAssembler {
    public static MedicationAdministrationResource toResourceFromEntity(MedicationAdministration entity)
    {
        return new MedicationAdministrationResource( entity.getId(),entity.getResident().getId(),entity.getStaffMemberId().staffMemberId()
                ,entity.getMedication().getName(),entity.getAdministeredAt().administeredAt(),entity.getQuantityAdministered(),entity.getWasAdministered(),entity.getNotes());

    }
}
