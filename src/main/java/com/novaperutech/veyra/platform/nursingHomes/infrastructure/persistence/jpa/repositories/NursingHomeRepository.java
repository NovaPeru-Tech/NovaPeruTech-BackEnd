package com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Name;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.PhoneNumber;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for nursing home persistence operations.
 * This interface extends JpaRepository to provide standard CRUD operations
 * and defines custom query methods for checking the existence of nursing homes
 * based on unique identifiers such as name, RUC, and phone number.
 * Spring Data JPA automatically implements this interface at runtime.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHome
 * @see JpaRepository
 */
@Repository
public interface NursingHomeRepository  extends JpaRepository< NursingHome,Long> {
    boolean existsByName(Name name);
    boolean existsByRuc(Ruc ruc);
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
 }
