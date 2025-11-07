package com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.PersonProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident,Long> {
    Optional<Resident>findByPersonProfileId( PersonProfileId personProfileId);
}
