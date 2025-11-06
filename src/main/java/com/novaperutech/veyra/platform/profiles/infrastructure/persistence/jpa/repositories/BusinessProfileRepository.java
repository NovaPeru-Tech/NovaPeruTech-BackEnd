package com.novaperutech.veyra.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.BusinessProfile;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.BusinessName;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile,Long> {
    boolean existsByBusinessName(BusinessName businessName);

    boolean existsByRuc(Ruc ruc);

}
