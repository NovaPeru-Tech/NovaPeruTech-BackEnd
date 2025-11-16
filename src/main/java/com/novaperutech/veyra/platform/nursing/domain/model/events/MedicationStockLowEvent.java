package com.novaperutech.veyra.platform.nursing.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MedicationStockLowEvent extends ApplicationEvent {
    private final Long medicationId;
  private final String name;
  private final Long residentId;
    public MedicationStockLowEvent(Object source, Long medicationId, String name, Long residentId){
        super(source);
        this.medicationId=medicationId;
           this.name=name;
        this.residentId = residentId;
    }

}
