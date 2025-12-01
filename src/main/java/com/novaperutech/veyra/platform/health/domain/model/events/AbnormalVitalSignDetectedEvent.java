package com.novaperutech.veyra.platform.health.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AbnormalVitalSignDetectedEvent extends ApplicationEvent {
    public AbnormalVitalSignDetectedEvent(Object source) {
        super(source);
    }
}
