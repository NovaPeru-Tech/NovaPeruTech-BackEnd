package com.novaperutech.veyra.platform.family.domain.model.aggregates;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.ResidentId;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.UserId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
public class FamilyMember extends AuditableAbstractAggregateRoot<FamilyMember> {
    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    private UserId userId;
    @ElementCollection
    @CollectionTable(
            name = "family_member_residents",
            joinColumns = @JoinColumn(name = "family_member_id")
    )
    @AttributeOverrides({@AttributeOverride(name = "residentId",
                    column = @Column(name = "resident_id", nullable = false))
    })
    private Set<ResidentId> residentIds = new HashSet<>();
    protected FamilyMember() {}
    public FamilyMember(UserId userId) {this.userId = userId;this.residentIds = new HashSet<>();}
    public void addResident(ResidentId residentId) {
        this.residentIds.add(residentId);
    }
    public boolean hasResident(ResidentId residentId) {
        return this.residentIds.contains(residentId);
    }
}