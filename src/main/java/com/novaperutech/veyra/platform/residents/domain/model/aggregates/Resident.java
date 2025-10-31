package com.novaperutech.veyra.platform.residents.domain.model.aggregates;

import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.BloodType;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Resident extends AbstractAggregateRoot<Resident> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;
    private String name;
    private String lastname;
    private String dni;
    private LocalDate birthDate;
    private Integer age;
    private String image;
    private String room;
    private String phoneNumber;
    private String email;
    private BloodType bloodType;
    private String allergies;

    @ElementCollection
    private List<String> chronicDiseases;

    private String currentMedications;
    private String specialDiet;
    private String mobilityLevel;
    private String dependencyLevel;
    private boolean needsBathingAssistance;
    private boolean needsFeedingAssistance;
    private boolean needsDressingAssistance;

    private String emergencyContactName;
    private String emergencyPhone;
    private String contactRelation;
    private String secondaryContact;
    private String secondaryPhone;

    private LocalDate admissionDate;
    private String attendingPhysician;
    private String medicalInsurance;
    private String socialSecurityNumber;

    private String legalGuardian;
    private String guardianPhone;

    protected Resident() {}

    public Resident(CreateResidentCommand command) {
        this.state = command.state();
        this.name = command.name();
        this.lastname = command.lastname();
        this.dni = command.dni();
        this.birthDate = command.birthDate();
        this.age = command.age();
        this.image = command.image();
        this.room = command.room();
        this.phoneNumber = command.phoneNumber();
        this.email = command.email();
        this.bloodType = command.bloodType();
        this.allergies = command.allergies();
        this.chronicDiseases = command.chronicDiseases();
        this.currentMedications = command.currentMedications();
        this.specialDiet = command.specialDiet();
        this.mobilityLevel = command.mobilityLevel();
        this.dependencyLevel = command.dependencyLevel();
        this.needsBathingAssistance = command.needsBathingAssistance();
        this.needsFeedingAssistance = command.needsFeedingAssistance();
        this.needsDressingAssistance = command.needsDressingAssistance();
        this.emergencyContactName = command.emergencyContactName();
        this.emergencyPhone = command.emergencyPhone();
        this.contactRelation = command.contactRelation();
        this.secondaryContact = command.secondaryContact();
        this.secondaryPhone = command.secondaryPhone();
        this.admissionDate = command.admissionDate();
        this.attendingPhysician = command.attendingPhysician();
        this.medicalInsurance = command.medicalInsurance();
        this.socialSecurityNumber = command.socialSecurityNumber();
        this.legalGuardian = command.legalGuardian();
        this.guardianPhone = command.guardianPhone();
    }

    public void update(UpdateResidentCommand command) {
        if (command.state() != null) this.state = command.state();
        if (command.name() != null) this.name = command.name();
        if (command.lastname() != null) this.lastname = command.lastname();
        if (command.dni() != null) this.dni = command.dni();
        if (command.birthDate() != null) this.birthDate = command.birthDate();
        if (command.age() != null) this.age = command.age();
        if (command.image() != null) this.image = command.image();
        if (command.room() != null) this.room = command.room();
        if (command.phoneNumber() != null) this.phoneNumber = command.phoneNumber();
        if (command.email() != null) this.email = command.email();
        if (command.bloodType() != null) this.bloodType = command.bloodType();
        if (command.allergies() != null) this.allergies = command.allergies();
        if (command.chronicDiseases() != null) this.chronicDiseases = command.chronicDiseases();
        if (command.currentMedications() != null) this.currentMedications = command.currentMedications();
        if (command.specialDiet() != null) this.specialDiet = command.specialDiet();
        if (command.mobilityLevel() != null) this.mobilityLevel = command.mobilityLevel();
        if (command.dependencyLevel() != null) this.dependencyLevel = command.dependencyLevel();
        this.needsBathingAssistance = command.needsBathingAssistance();
        this.needsFeedingAssistance = command.needsFeedingAssistance();
        this.needsDressingAssistance = command.needsDressingAssistance();
        if (command.emergencyContactName() != null) this.emergencyContactName = command.emergencyContactName();
        if (command.emergencyPhone() != null) this.emergencyPhone = command.emergencyPhone();
        if (command.contactRelation() != null) this.contactRelation = command.contactRelation();
        if (command.secondaryContact() != null) this.secondaryContact = command.secondaryContact();
        if (command.secondaryPhone() != null) this.secondaryPhone = command.secondaryPhone();
        if (command.admissionDate() != null) this.admissionDate = command.admissionDate();
        if (command.attendingPhysician() != null) this.attendingPhysician = command.attendingPhysician();
        if (command.medicalInsurance() != null) this.medicalInsurance = command.medicalInsurance();
        if (command.socialSecurityNumber() != null) this.socialSecurityNumber = command.socialSecurityNumber();
        if (command.legalGuardian() != null) this.legalGuardian = command.legalGuardian();
        if (command.guardianPhone() != null) this.guardianPhone = command.guardianPhone();
    }
}
