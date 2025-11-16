package com.novaperutech.veyra.platform.nursing.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class RegisterResidentEvent extends ApplicationEvent {
    private final Long residentId;
    private final Long nursingHomeId;
    private final String nursingHomeName;
    private final Integer age;
    private final LocalDateTime registeredAt;
    public RegisterResidentEvent(Object source, Long residentId, Long nursingHomeId, String nursingHomeName, Integer age, LocalDateTime registeredAt) {
        super(source);
        this.residentId=residentId;
        this.nursingHomeId = nursingHomeId;
        this.nursingHomeName = nursingHomeName;
        this.age = age;
        this.registeredAt = registeredAt;
    }
}
