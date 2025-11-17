/**
 * Domain event triggered when a new resident is successfully registered
 * in a nursing home within the system.
 *
 * <p>This event is published to notify interested listeners that a resident
 * has been added, providing contextual information such as the nursing home,
 * the resident's age, and the timestamp of registration.</p>
 *
 * @summary Event emitted upon registering a new resident with related metadata.
 */
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
        this.residentId = residentId;
        this.nursingHomeId = nursingHomeId;
        this.nursingHomeName = nursingHomeName;
        this.age = age;
        this.registeredAt = registeredAt;
    }
}
