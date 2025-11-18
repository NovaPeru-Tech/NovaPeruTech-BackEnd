package com.novaperutech.veyra.platform.hcm.domain.model.aggregates;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.ContractHistory;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.EmergencyContact;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.NursingHomeId;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.PersonProfileId;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Staff extends AuditableAbstractAggregateRoot<Staff> {
 protected Staff(){

     super();
     contractHistory=new ContractHistory();
 }
    @Embedded
    private final ContractHistory contractHistory;

    @Embedded
 private    EmergencyContact emergencyContact;
    @Embedded
    private NursingHomeId nursingHomeId;
 @Embedded
 @AttributeOverride(name = "id", column = @Column(name = "person_profile_id"))
private PersonProfileId personProfileId;
public Staff(Long personProfileId,String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber){
    this();
    this.personProfileId=new PersonProfileId(personProfileId);
    this.emergencyContact=new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
}
 public Staff(PersonProfileId personProfileId,NursingHomeId nursingHomeId,EmergencyContact emergencyContact){
    this();
     this.personProfileId=personProfileId;
     this.emergencyContact=emergencyContact;
     this.nursingHomeId=nursingHomeId;
 }
 public Staff updateEmergencyContact(String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber)
 {
     this.emergencyContact= new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
     return this;
 }

    public void addContractToHistory(LocalDate startDate, LocalDate endDate,
                                     String typeOfContract, String staffRole, String workShift) {
        this.contractHistory.addContract(this, startDate, endDate, typeOfContract, staffRole, workShift);
    }

    public void updateContractStatus(Long contractId, String newStatus) {
        this.contractHistory.updateContractStatus(contractId, newStatus);
    }
}
