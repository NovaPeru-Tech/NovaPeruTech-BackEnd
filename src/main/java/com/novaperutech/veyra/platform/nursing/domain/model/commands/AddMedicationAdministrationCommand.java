package com.novaperutech.veyra.platform.nursing.domain.model.commands;

import java.time.LocalDateTime;

public record AddMedicationAdministrationCommand(Long residentId,String medicationName, Long staffMemberId,
                                                 LocalDateTime administeredAt,Integer quantityAdministered, boolean wasAdministered, String notes) {
    public AddMedicationAdministrationCommand{
        if (residentId==null|| residentId<1){
            throw new IllegalArgumentException("resident id cannot be null or less 1");
        }
        if (medicationName==null||medicationName.isEmpty()){
            throw new IllegalArgumentException("medication name ");
        }
        if( staffMemberId==null||staffMemberId<=0)
        {
            throw new IllegalArgumentException("staff member id cannot be null or less 0");
        }
    }
}
