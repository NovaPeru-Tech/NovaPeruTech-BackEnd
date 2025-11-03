package com.novaperutech.veyra.platform.residents.interfaces.rest.transform;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.ResidentResource;

public class ResidentResourceFromEntityAssembler {

    public static ResidentResource toResource(Resident entity) {
        return new ResidentResource(
                entity.getId(),
                entity.getState(),
                entity.getName(),
                entity.getLastname(),
                entity.getDni(),
                entity.getBirthDate(),
                entity.getAge(),
                entity.getImage(),
                entity.getRoom(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getEmergencyContact().getName().getFullName(),
                entity.getEmergencyContact().getPhoneNumber().phoneNumber(),
                entity.getEmergencyContact().getRelationShip().toString(),
                entity.getLegalRepresentative().getName().getFullName(),
                entity.getLegalRepresentative().getPhoneNumber().phoneNumber()
        );
    }
}
