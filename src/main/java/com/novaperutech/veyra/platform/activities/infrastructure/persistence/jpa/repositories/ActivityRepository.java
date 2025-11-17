package com.novaperutech.veyra.platform.activities.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.activities.domain.model.aggregates.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * ESTA ES LA "OTRA FORMA".
     * Es una Consulta Derivada (Derived Query).
     * No escribes JPQL ni SQL. Solo defines la firma del método y Spring Data
     * JPA automáticamente crea la consulta:
     * "SELECT a FROM Activity a WHERE a.activityDate = ?1 AND a.nursingHomeId.nursingHomeId = ?2 ORDER BY a.period.startTime"
     *
     * Esto devolverá una List<Activity>, pero NO traerá los nombres.
     */
    List<Activity> findByActivityDateAndNursingHomeId_NursingHomeIdOrderByPeriod_StartTime(
            LocalDate date,
            Long nursingHomeId
    );
}