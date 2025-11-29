package com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.family.domain.model.aggregates.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessCodeRepository extends JpaRepository<AccessCode, Long > {
    Optional<AccessCode> findByCode(String code);
    Optional<AccessCode> findByFamilyEmailAndUsedFalse(String familyEmail);
}
