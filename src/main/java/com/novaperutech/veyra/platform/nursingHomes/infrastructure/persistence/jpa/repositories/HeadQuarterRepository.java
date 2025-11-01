package com.novaperutech.veyra.platform.nursingHomes.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.entities.Headquarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * JPA repository interface for {@link Headquarter} entity persistence operations.
 * <p>
 * This repository provides CRUD operations and custom query methods for managing
 * headquarter entities in the database. It extends Spring Data JPA's {@link JpaRepository}
 * to inherit standard data access functionality.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see Headquarter
 * @see JpaRepository
 */
@Repository
public interface HeadQuarterRepository extends JpaRepository<Headquarter,Long> {
    /**
     * Retrieves all headquarters associated with a specific nursing home.
     * <p>
     * This method performs a query to find all headquarter entities that belong
     * to the nursing home identified by the provided ID.
     * </p>
     *
     * @param nursingHomeId the unique identifier of the nursing home
     * @return a list of headquarters belonging to the specified nursing home,
     *         or an empty list if no headquarters are found
     */
    List<Headquarter>findAllByNursingHomeId(Long nursingHomeId);
}
