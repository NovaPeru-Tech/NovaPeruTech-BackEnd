package com.novaperutech.veyra.platform.family.interfaces.rest.transform;

import com.novaperutech.veyra.platform.family.domain.model.aggregates.FamilyMember;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.FamilyMemberResource;

public class FamilyMemberResourceFromEntityAssembler {
    public static FamilyMemberResource toResourceFromEntity(FamilyMember entity){
        return new FamilyMemberResource(entity.getId(),entity.getUserId().userId(), (long) entity.getResidentIds().size());
    }
}
