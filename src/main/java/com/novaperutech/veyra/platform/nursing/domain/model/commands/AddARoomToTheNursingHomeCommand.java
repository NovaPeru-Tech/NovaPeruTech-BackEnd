package com.novaperutech.veyra.platform.nursing.domain.model.commands;

public record AddARoomToTheNursingHomeCommand(Long nursingHomeId, Integer capacity,
                                              String type) {
}
