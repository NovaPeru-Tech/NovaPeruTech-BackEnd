package com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.BusinessProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NursingHomeRepository extends JpaRepository<NursingHome,Long> {
boolean existsByBusinessProfileId(BusinessProfileId businessProfileId);
Optional<NursingHome> findByBusinessProfileId(BusinessProfileId businessProfileId);
}
