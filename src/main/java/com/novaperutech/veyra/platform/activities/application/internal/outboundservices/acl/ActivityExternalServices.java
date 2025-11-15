package com.novaperutech.veyra.platform.activities.application.internal.outboundservices.acl;

// IMPORTANTE: Ya NO importamos 'hcm'
import com.novaperutech.veyra.platform.nursing.interfaces.acl.NursingContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ActivityExternalServices {

    private final NursingContextFacade nursingContextFacade;

    /**
     * Constructor actualizado (sin HcmContextFacade).
     * Spring ahora solo inyectará la fachada de Nursing.
     */
    public ActivityExternalServices(NursingContextFacade nursingContextFacade) {
        this.nursingContextFacade = nursingContextFacade;
    }

    /**
     * Como nos dijo tu líder, quitamos la dependencia de HCM.
     * Dejamos la simulación temporal para que el código compile.
     * TODO: Cuando HCM esté listo, se deberá re-implementar esto.
     */
    public boolean staffExists(Long staffId) {
        System.out.println(
                "ADVERTENCIA: Simulación temporal de staffExists. " +
                        "Siempre devolverá 'true'."
        );

        if (staffId == null || staffId <= 0) return false;
        return true; // Simulación temporal
    }

    /**
     * Esto sigue siendo una simulación, porque la fachada de Nursing
     * NO nos da el método 'existsResidentById'.
     *
     *
     * TODO: Reemplazar 'return true' cuando el equipo de Nursing
     * exponga el método 'existsResidentById(Long id)' en su fachada.
     */
    public boolean residentExists(Long residentId) {
        if (residentId == null || residentId <= 0) return false;
        return true; // Simulación temporal
    }
}