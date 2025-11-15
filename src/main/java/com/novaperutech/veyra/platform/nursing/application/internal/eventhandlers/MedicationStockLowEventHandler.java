package com.novaperutech.veyra.platform.nursing.application.internal.eventhandlers;

import com.novaperutech.veyra.platform.nursing.domain.services.MedicationCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentCommandServices;
import org.springframework.stereotype.Service;

@Service
public class MedicationStockLowEventHandler {
    private  final MedicationCommandServices medicationCommandServices;
    private final ResidentCommandServices residentCommandServices;
    public MedicationStockLowEventHandler(MedicationCommandServices medicationCommandServices, ResidentCommandServices residentCommandServices) {
        this.medicationCommandServices = medicationCommandServices;
        this.residentCommandServices = residentCommandServices;
    }
}
