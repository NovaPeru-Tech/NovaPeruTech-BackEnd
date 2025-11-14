package com.novaperutech.veyra.platform.nursing.domain.model.entities;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Medication;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.AdministeredAt;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.StaffMemberId;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class MedicationAdministration extends AuditableModel{
    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;
    @NotNull
@Column(name = "staff_member_id")
@Embedded
    private StaffMemberId staffMemberId;
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;
@Embedded
private AdministeredAt administeredAt;
    @Column(nullable = false)
    private Boolean wasAdministered;
    @Column(length = 500)
    private String notes;
    @Column(nullable = false)
private Integer quantityAdministered;

 public MedicationAdministration( Resident resident,
                                  Medication medication,
                                  StaffMemberId staffMemberId,
                                  AdministeredAt administeredAt,
                                  Integer quantityAdministered,
                                  Boolean wasAdministered,
                                  String notes)
{
    this.medication=medication;
    this.staffMemberId=staffMemberId;
    this.resident=resident;
    this.wasAdministered=wasAdministered;
    this.administeredAt=administeredAt;
    this.notes=notes;
    this.quantityAdministered=quantityAdministered;
}
}
