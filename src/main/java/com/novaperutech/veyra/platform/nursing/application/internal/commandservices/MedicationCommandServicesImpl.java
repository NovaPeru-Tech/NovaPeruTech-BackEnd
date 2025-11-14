package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Medication;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateMedicationCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.DrugPresentation;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.ExpirationDate;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.ResidentState;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.Stock;
import com.novaperutech.veyra.platform.nursing.domain.services.MedicationCommandServices;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.MedicationRepository;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.ResidentRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicationCommandServicesImpl implements MedicationCommandServices {
    private final MedicationRepository medicationRepository;
    private final ResidentRepository residentRepository;

    public MedicationCommandServicesImpl(MedicationRepository medicationRepository, ResidentRepository residentRepository) {
        this.medicationRepository = medicationRepository;
        this.residentRepository = residentRepository;
    }

    @Override
    public Long handle(CreateMedicationCommand command) {
       var resident=residentRepository.findById(command.residentId())
               .orElseThrow(()->new IllegalArgumentException("resident not found"));

        if (!resident.getResidentStatus().equals(ResidentState.ACTIVE))
        {
            throw new IllegalArgumentException("resident not active");
        }
        if (medicationRepository.existsByResidentIdAndName(command.residentId(), command.name())){
            throw new IllegalArgumentException("Medication '" + command.name() + "' already exists for this resident");

        }
        var stock= new Stock(command.amount());
        var expirationDate= new ExpirationDate(command.expirationDate());
        var drugPresentation= DrugPresentation.valueOf(command.drugPresentation());
        var medication= new Medication(command.name(),command.description(),stock,expirationDate,drugPresentation,command.dosage(),resident);
        try {
            medicationRepository.save(medication);

        }catch (Exception e){
            throw new IllegalArgumentException("Error while adding contract to medication: %s".formatted(e.getMessage()));
        }
        return medication.getId();
    }
}
