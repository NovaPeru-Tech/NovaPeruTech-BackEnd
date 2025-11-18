package com.novaperutech.veyra.platform.nursing.domain.services;


import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateARoomToTheNursingHomeCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.AssignRoomForResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.ChangeOfRoomForTheResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateNursingHomeCommand;

public interface NursingHomeCommandServices {
    Long handle (CreateNursingHomeCommand command);
    void handle(CreateARoomToTheNursingHomeCommand command);
    void handle(ChangeOfRoomForTheResidentCommand command);
    void handle(AssignRoomForResidentCommand command);
}
