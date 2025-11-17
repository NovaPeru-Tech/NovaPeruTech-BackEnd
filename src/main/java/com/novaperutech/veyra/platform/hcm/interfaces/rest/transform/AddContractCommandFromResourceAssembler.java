package com.novaperutech.veyra.platform.hcm.interfaces.rest.transform;

import com.novaperutech.veyra.platform.hcm.domain.model.commands.AddContractToStaffCommand;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.AddContractResource;

public class AddContractCommandFromResourceAssembler {
public static AddContractToStaffCommand toCommandFromResource(Long id,AddContractResource resource){
    return new AddContractToStaffCommand(id,resource.startDate(),resource.endDate(),resource.typeOfContract(),resource.staffRole(),
            resource.workShift());
}
}
