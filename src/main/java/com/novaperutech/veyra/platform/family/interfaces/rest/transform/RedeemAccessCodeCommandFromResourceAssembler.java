package com.novaperutech.veyra.platform.family.interfaces.rest.transform;

import com.novaperutech.veyra.platform.family.domain.model.commands.RedeemAccessCodeCommand;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.RedeemAccessCodeResource;

public class RedeemAccessCodeCommandFromResourceAssembler {
    public static RedeemAccessCodeCommand toCommandFromResource(RedeemAccessCodeResource resource,Long userId){
        return new RedeemAccessCodeCommand(userId,resource.code());
    }
}
