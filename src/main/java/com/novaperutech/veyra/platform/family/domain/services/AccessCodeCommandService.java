package com.novaperutech.veyra.platform.family.domain.services;

import com.novaperutech.veyra.platform.family.domain.model.commands.GenerateAccessCodeCommand;
import com.novaperutech.veyra.platform.family.domain.model.commands.RedeemAccessCodeCommand;

public interface AccessCodeCommandService {
    String handle(GenerateAccessCodeCommand command);
    void handle(RedeemAccessCodeCommand command);
}
