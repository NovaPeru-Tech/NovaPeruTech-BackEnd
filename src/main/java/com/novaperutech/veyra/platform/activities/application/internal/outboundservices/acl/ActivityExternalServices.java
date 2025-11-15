package com.novaperutech.veyra.platform.activities.application.internal.outboundservices.acl;

// Importa las Fachadas de los otros contextos
import com.novaperutech.veyra.platform.hcm.interfaces.acl.HcmContextFacade;
import com.novaperutech.veyra.platform.nursing.interfaces.acl.NursingContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ActivityExternalServices {

    private final NursingContextFacade nursingContextFacade;
    private final HcmContextFacade hcmContextFacade;

    public ActivityExternalServices(NursingContextFacade nursingContextFacade, HcmContextFacade hcmContextFacade) {
        this.nursingContextFacade = nursingContextFacade;
        this.hcmContextFacade = hcmContextFacade;
    }

    public boolean staffExists(Long staffId) {
        // TODO: Implementar hcmContextFacade.existsStaffById(staffId);
        // Esto asume que agregarás 'existsStaffById(Long id)' a tu 'HcmContextFacade'
        // return hcmContextFacade.existsStaffById(staffId);
        if (staffId == null || staffId <= 0) return false;
        return true; // Simulación temporal
    }

    public boolean residentExists(Long residentId) {
        // TODO: Implementar nursingContextFacade.existsResidentById(residentId);
        // Esto asume que agregarás 'existsResidentById(Long id)' a tu 'NursingContextFacade'
        // return nursingContextFacade.existsResidentById(residentId);
        if (residentId == null || residentId <= 0) return false;
        return true; // Simulación temporal
    }
}