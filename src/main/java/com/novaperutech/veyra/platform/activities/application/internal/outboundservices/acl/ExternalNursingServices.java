package com.novaperutech.veyra.platform.activities.application.internal.outboundservices.acl;
import com.novaperutech.veyra.platform.activities.domain.model.valueobjects.NursingHomeId;
import com.novaperutech.veyra.platform.nursing.interfaces.acl.NursingContextFacade;
import org.springframework.stereotype.Service;

@Service

public class ExternalNursingServices {
    private final NursingContextFacade nursingContextFacade;
    public ExternalNursingServices(NursingContextFacade nursingContextFacade) {
        this.nursingContextFacade = nursingContextFacade;
    }
    public Opcional<NursingHomeId>
}
