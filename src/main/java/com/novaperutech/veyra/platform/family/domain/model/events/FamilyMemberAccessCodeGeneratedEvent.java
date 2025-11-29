package com.novaperutech.veyra.platform.family.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class FamilyMemberAccessCodeGeneratedEvent extends ApplicationEvent {
  private final  String code;
  private final String familyEmail;
  private final Integer validityDays;
    public FamilyMemberAccessCodeGeneratedEvent(Object source, String code, String familyEmail, Integer validityDays) {
        super(source);
        this.code = code;
        this.familyEmail = familyEmail;
        this.validityDays = validityDays;
    }

}
