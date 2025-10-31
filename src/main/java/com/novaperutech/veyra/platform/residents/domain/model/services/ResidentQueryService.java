package com.novaperutech.veyra.platform.residents.domain.model.services;

import com.novaperutech.veyra.platform.residents.domain.model.aggregates.Resident;

import java.util.List;
import java.util.Optional;

public interface ResidentQueryService {

    // Obtener todos los residentes
    List<Resident> handle();

    // Obtener por ID
    Optional<Resident> handle(Long id);

    // Obtener por estado
    List<Resident> handle(String state);
}
