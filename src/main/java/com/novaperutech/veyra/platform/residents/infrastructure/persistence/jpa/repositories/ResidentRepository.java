package com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

}
