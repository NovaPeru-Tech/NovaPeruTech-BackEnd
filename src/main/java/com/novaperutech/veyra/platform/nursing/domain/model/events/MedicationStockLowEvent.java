package com.novaperutech.veyra.platform.nursing.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MedicationStockLowEvent extends ApplicationEvent {
    private final Long medicationId;

    public MedicationStockLowEvent(Object source,Long medicationId){
        super(source);
        this.medicationId=medicationId;

    }

}
