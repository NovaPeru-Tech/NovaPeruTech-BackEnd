package com.novaperutech.veyra.platform.family.domain.model.aggregates;

import com.novaperutech.veyra.platform.family.domain.model.valueobjects.ResidentId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class AccessCode extends AuditableAbstractAggregateRoot<AccessCode> {
    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Embedded
    private ResidentId residentId;

    @Column(nullable = false)
    private String familyEmail;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used;

    protected AccessCode() {}

    public AccessCode(String code, ResidentId residentId, String familyEmail, int validityDays) {
        this.code = code;
        this.residentId = residentId;
        this.familyEmail = familyEmail;
        this.expiresAt = LocalDateTime.now().plusDays(validityDays);
        this.used = false;
    }

    public boolean isValid() {
        return used || !LocalDateTime.now().isBefore(expiresAt);
    }

    public void markAsUsed() {
        if (isValid()) {
            throw new IllegalStateException("Access code is not valid");
        }
        this.used = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
