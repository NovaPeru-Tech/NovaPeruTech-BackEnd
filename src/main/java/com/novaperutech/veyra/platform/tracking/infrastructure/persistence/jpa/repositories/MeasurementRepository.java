package com.novaperutech.veyra.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.tracking.domain.model.aggregates.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
