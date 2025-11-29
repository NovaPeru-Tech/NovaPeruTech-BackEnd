package com.novaperutech.veyra.platform.family.interfaces.rest.transform;
import com.novaperutech.veyra.platform.family.domain.model.aggregates.AccessCode;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.AccessCodeResource;
public class AccessCodeCommandFromResourceAssembler {
    public static AccessCodeResource toResourceFromEntity(AccessCode entity){
    return new AccessCodeResource(entity.getCode(),entity.getFamilyEmail(),entity.getResidentId().residentId(),entity.getExpiresAt()) ;
    }
}
