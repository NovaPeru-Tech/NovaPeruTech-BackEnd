package com.novaperutech.veyra.platform.nursing.domain.services;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateFamiliarCommand;

public interface FamiliarCommandService {
    Long handle(CreateFamiliarCommand command);
}
