package com.novaperutech.veyra.platform.nursing.interfaces.rest.transform;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.ResidentResource;

public class ResidentResourceFromEntityAssembler {
    public static ResidentResource toResourceFromEntity(Resident entity){
        return new ResidentResource(entity.getId(), entity.getPersonProfileId().personProfileId()
                ,entity.getLegalRepresentative().firstName(),entity.getLegalRepresentative().lastName(),entity.getLegalRepresentative().phoneNumber()
                ,entity.getEmergencyContact().firstName(),entity.getEmergencyContact().lastName(),entity.getEmergencyContact().phoneNumber());
    }
}
