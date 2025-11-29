package com.novaperutech.veyra.platform.family.application.internal.outboundservices.acl;

import com.novaperutech.veyra.platform.nursing.interfaces.acl.NursingContextFacade;
import com.novaperutech.veyra.platform.tracking.domain.model.valueobjects.ResidentId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("NursingExternalServiceFamily")
public class ExternalNursingService {
    private final NursingContextFacade nursingContextFacade;

    public ExternalNursingService(NursingContextFacade nursingContextFacade) {
        this.nursingContextFacade = nursingContextFacade;
    }

   public  Optional<ResidentId>fetchResidentById(Long id){
        var residentId=nursingContextFacade.fetchResidentById(id);
        return residentId==0L?Optional.empty():Optional.of(new ResidentId(residentId));
    }
}
