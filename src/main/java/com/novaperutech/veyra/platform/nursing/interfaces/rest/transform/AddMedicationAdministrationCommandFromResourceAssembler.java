package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.AddMedicationAdministrationCommand;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.AddMedicationAdministrationResource;

public class AddMedicationAdministrationCommandFromResourceAssembler {
    public static AddMedicationAdministrationCommand toCommandFromResource(AddMedicationAdministrationResource resource,Long residentId){
     return new AddMedicationAdministrationCommand(residentId,resource.medicationName(),resource.staffMemberId(),resource.administeredAt(),resource.quantityAdministered()
     ,resource.wasAdministered(),resource.notes());
    }
}
