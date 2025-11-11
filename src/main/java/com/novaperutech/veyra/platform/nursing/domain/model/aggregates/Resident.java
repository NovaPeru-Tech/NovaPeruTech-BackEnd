package com.novaperutech.veyra.platform.nursing.domain.model.aggregates;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.EmergencyContact;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.LegalRepresentative;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.PersonProfileId;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.ResidentState;
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
    private EmergencyContact emergencyContact;
    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "firstName",column = @Column(name =  "legal_representative_firstname")),
                    @AttributeOverride(name = "lastName",column = @Column(name =   "legal_representative_lastname")),
                    @AttributeOverride(name = "phoneNumber",column = @Column(name ="legal_representative_phone_number"))
            }
    )
    private LegalRepresentative legalRepresentative;
    @ManyToOne
    @JoinColumn(name = "nursing_home_id")
    private NursingHome nursingHome;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ResidentState residentStatus;
    public Resident(Long personProfileId,String legalRepresentativeFirstName,String legalRepresentativeLastName,String legalRepresentativePhoneNumber
               ,String emergencyContactFirstName,String emergencyContactLastName,String emergencyContactPhoneNumber){
        this();
        this.personProfileId = new PersonProfileId(personProfileId);
        this.legalRepresentative= new LegalRepresentative(legalRepresentativeFirstName,legalRepresentativeLastName,legalRepresentativePhoneNumber);
        this.emergencyContact= new EmergencyContact(emergencyContactFirstName,emergencyContactLastName,emergencyContactPhoneNumber);
    }
public Resident(NursingHome nursingHome,PersonProfileId personProfileId, LegalRepresentative legalRepresentative,EmergencyContact emergencyContact)
{
    this();
    this.personProfileId=  personProfileId;
    this.legalRepresentative= legalRepresentative;
    this.emergencyContact= emergencyContact;
    this.nursingHome=nursingHome;
    this.residentStatus= ResidentState.ACTIVE;
}
    public void activate (){
        if (this.residentStatus==ResidentState.ACTIVE){
            throw new IllegalStateException("Resident is already exists");
        }
        if (this.residentStatus==ResidentState.RETIRED){
            throw new IllegalStateException("A voluntary withdrawal cannot be reactivated. Voluntary withdrawals are final.");
        }
        if (this.residentStatus==ResidentState.DECEASED)
        {
            throw new IllegalStateException("The status of a deceased person cannot be reactivated.");

        }
        this.residentStatus=ResidentState.ACTIVE;
    }
    public void retired(){
        if (this.residentStatus == ResidentState.RETIRED) {
            throw new IllegalStateException("Resident already retired");
        }
        if (this.residentStatus == ResidentState.DECEASED) {
            throw new IllegalStateException("A deceased resident cannot be retired");
        }
        if (this.residentStatus != ResidentState.ACTIVE) {
            throw new IllegalStateException(
                    "Can only suspend an active resident. Current status: " + this.residentStatus
            );
        }
        this.residentStatus=ResidentState.RETIRED;
    }
    public void deceased(){
        if (this.residentStatus == ResidentState.DECEASED) {
            throw new IllegalStateException("A deceased resident cannot be retired");
        }
        if (this.residentStatus == ResidentState.RETIRED) {
            throw new IllegalStateException("Resident already retired");
        }
        if (this.residentStatus != ResidentState.ACTIVE) {
            throw new IllegalStateException(
                    "Can only suspend an active resident. Current status: " + this.residentStatus
            );
        }
        this.residentStatus=ResidentState.DECEASED;
    }
    public void updateStatus(ResidentState newStatus){
        switch (newStatus) {
            case ACTIVE:
                activate();
                break;
            case RETIRED:
                retired();break;
                case DECEASED:
                    deceased();break;
            default:
                throw new IllegalStateException("Invalid resident state");
        }
    }
    public boolean isActive(){return this.residentStatus==ResidentState.ACTIVE;}
    public boolean isFinalState(){return this.residentStatus==ResidentState.RETIRED||this.residentStatus==ResidentState.DECEASED;}
    public Resident updateLegalRepresentative(String firstName, String lastName, String phoneNumber) {
        this.legalRepresentative = new LegalRepresentative(firstName, lastName, phoneNumber);
        return this;
    }
    public Resident updateEmergencyContact(String firstName, String lastName, String phoneNumber) {
        this.emergencyContact = new EmergencyContact(firstName, lastName, phoneNumber);
        return this;
    }
}