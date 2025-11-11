package com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.PersonProfileId;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjects.ResidentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident,Long> {
    Optional<Resident>findByPersonProfileId( PersonProfileId personProfileId);
    List<Resident>findByNursingHomeId(Long nursingHomeId);
Optional<Resident>findByNursingHomeIdAndId(Long nursingHome_id, Long id);
    List<Resident> findByNursingHomeIdAndResidentStatus(Long nursingHomeId, ResidentState residentState);
    boolean existsByNursingHomeIdAndPersonProfileId(Long nursingHomeId, PersonProfileId personProfileId);
}
