package com.novaperutech.veyra.platform.residents.infrastructure.persistence.jpa;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    // Buscar por estado (por ejemplo: "active", "inactive")
    List<Resident> findByState(String state);

    // Buscar por nombre (opcional)
    List<Resident> findByNameContainingIgnoreCase(String name);

    // Buscar por DNI (para validar duplicados)
    boolean existsByDni(String dni);
}
