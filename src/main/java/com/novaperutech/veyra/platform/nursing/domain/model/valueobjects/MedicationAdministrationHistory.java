package com.novaperutech.veyra.platform.nursing.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Medication;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.entities.MedicationAdministration;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class MedicationAdministrationHistory {

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationAdministration> medicationAdministrations;

    public MedicationAdministrationHistory() {
        this.medicationAdministrations = new ArrayList<>();
    }

    /**
     * Add a new administration record
     */
    public void addAdministration(
            Resident resident,
            Medication medication,
            StaffMemberId staffMemberId,
            AdministeredAt administeredAt,
            Integer quantityAdministered,
            Boolean wasAdministered,
            String notes
    ) {
        if (wasAdministered){
            if (!medication.hasEnoughStock(quantityAdministered)){
             throw new IllegalArgumentException( String.format(
                     "Insufficient stock for medication '%s'. Available: %d, Requested: %d",
                     medication.getName(),
                     medication.getStock().amount(),
                     quantityAdministered));
            }
            medication.decreaseStock(quantityAdministered);
        }
        MedicationAdministration administration = new MedicationAdministration(
                resident,
                medication,
                staffMemberId,
                administeredAt,
                quantityAdministered,
                wasAdministered,
                notes
        );
        medicationAdministrations.add(administration);
    }

    /**
     * Get all administrations (immutable copy)
     */
    public List<MedicationAdministration> getAllAdministrations() {
        return new ArrayList<>(medicationAdministrations);
    }

    /**
     * Get administration by ID
     */
    public Optional<MedicationAdministration> getAdministrationById(Long administrationId) {
        return medicationAdministrations.stream()
                .filter(admin -> admin.getId().equals(administrationId))
                .findFirst();
    }

    /**
     * Check if history is empty
     */
    public boolean isEmpty() {
        return medicationAdministrations.isEmpty();
    }
    /**
     * Get total count of administrations
     */
    public int getCount() {
        return medicationAdministrations.size();
    }
}