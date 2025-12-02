package com.novaperutech.veyra.platform.nursing.domain.services;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Familiar;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetFamiliarByIdQuery;
import java.util.Optional;
public interface FamiliarQueryService {
    Optional<Familiar>handle(GetFamiliarByIdQuery query);
}
