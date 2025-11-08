package com.novaperutech.veyra.platform.hcm.domain.model.aggregates;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.EmergencyContact;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.PersonProfileId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Staff extends AuditableAbstractAggregateRoot<Staff> {
 protected Staff(){ super();}
    @Embedded
 private    EmergencyContact emergencyContact;
 @Embedded
 @AttributeOverride(name = "id", column = @Column(name = "person_profile_id"))
private PersonProfileId personProfileId;
public Staff(Long personProfileId,String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber){
    this();
    this.personProfileId=new PersonProfileId(personProfileId);
    this.emergencyContact=new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
}
 public Staff(PersonProfileId personProfileId,EmergencyContact emergencyContact){
    this();
     this.personProfileId=personProfileId;
     this.emergencyContact=emergencyContact;
 }
 public Staff updateEmergencyContact(String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber)
 {
     this.emergencyContact= new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
     return this;
 }

}
