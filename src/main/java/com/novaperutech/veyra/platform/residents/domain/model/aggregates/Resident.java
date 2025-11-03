package com.novaperutech.veyra.platform.residents.domain.model.aggregates;

import com.novaperutech.veyra.platform.residents.domain.model.commands.CreateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.commands.UpdateResidentCommand;
import com.novaperutech.veyra.platform.residents.domain.model.entities.EmergencyContact;
import com.novaperutech.veyra.platform.residents.domain.model.entities.LegalRepresentative;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDate;


@Entity
@Getter
public class Resident extends AuditableAbstractAggregateRoot<Resident> {
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
@NotNull
@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
@JoinColumn(name = "legal_representative_id")
private LegalRepresentative legalRepresentative;
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "emergency_contact_id")
    private EmergencyContact emergencyContact;
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
        this.legalRepresentative= new LegalRepresentative(command.firstGuardianName(),command.lastGuardianName(),command.guardianPhone());
        this.emergencyContact= new EmergencyContact(command.emergencyContactFirstName(),command.emergencyContactLastName(),command.emergencyContactPhoneNumber(),command.emergencyContactRelationship());
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

    }
}
