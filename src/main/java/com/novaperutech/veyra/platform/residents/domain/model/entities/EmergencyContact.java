package com.novaperutech.veyra.platform.residents.domain.model.entities;

import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.Name;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.PhoneNumber;
import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.RelationShip;
import com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.conversions.RelationShipConverter;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EmergencyContact extends AuditableModel {

@Embedded
@AttributeOverrides(
        value = {
                @AttributeOverride(name = "firstName", column = @Column(name = "emergency_contact_first_name")),
                @AttributeOverride(name = "lastName", column = @Column(name = "emergency_contact_last_name"))
        }
)
    private Name name;
@Embedded
@AttributeOverride(name="phoneNumber",column =@Column(name= "emergency_contact_phone_number"))
    private PhoneNumber phoneNumber;

@Convert(converter = RelationShipConverter.class)
@Column(nullable = false)
    private RelationShip relationShip;

    public EmergencyContact(String firstName, String lastName, String phoneNumber, String relationShip) {
        this.name = new Name(firstName, lastName);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.relationShip = RelationShip.valueOf(relationShip);
    }
}
