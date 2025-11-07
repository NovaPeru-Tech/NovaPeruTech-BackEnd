package com.novaperutech.veyra.platform.nursing.domain.model.aggregates;

import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.EmergencyContact;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.LegalRepresentative;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.PersonProfileId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Resident extends AuditableAbstractAggregateRoot<Resident> {
    protected Resident(){super();}
    @Embedded
    private PersonProfileId personProfileId;
    @Embedded
            @AttributeOverrides(
                    value = {
                            @AttributeOverride(name = "firstName",column = @Column(name = "emergency_firstname")),
                            @AttributeOverride(name = "lastName",column = @Column(name = "emergency_lastname")),
                            @AttributeOverride(name = "phoneNumber",column = @Column(name = "emergency_phone_number"))
                    }
            )
    EmergencyContact emergencyContact;
    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "firstName",column = @Column(name =  "legal_representative_firstname")),
                    @AttributeOverride(name = "lastName",column = @Column(name =   "legal_representative_lastname")),
                    @AttributeOverride(name = "phoneNumber",column = @Column(name ="legal_representative_phone_number"))
            }
    )
    private LegalRepresentative legalRepresentative;

    public Resident(Long personProfileId,String legalRepresentativeFirstName,String legalRepresentativeLastName,String legalRepresentativePhoneNumber
               ,String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber){
        this();
        this.personProfileId = new PersonProfileId(personProfileId);
        this.legalRepresentative= new LegalRepresentative(legalRepresentativeFirstName,legalRepresentativeLastName,legalRepresentativePhoneNumber);
        this.emergencyContact= new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
    }
public Resident(PersonProfileId personProfileId, LegalRepresentative legalRepresentative,EmergencyContact emergencyContact)
{
    this();
    this.personProfileId=  personProfileId;
    this.legalRepresentative= legalRepresentative;
    this.emergencyContact= emergencyContact;
}
    public Resident updateLegalRepresentative(String firstName, String lastName, String phoneNumber) {
        this.legalRepresentative = new LegalRepresentative(firstName, lastName, phoneNumber);
        return this;
    }

    public Resident updateEmergencyContact(String firstName, String lastName, String phoneNumber) {
        this.emergencyContact = new EmergencyContact(firstName, lastName, phoneNumber);
        return this;
    }

}
