package com.novaperutech.veyra.platform.health.domain.model.commands;

public record ValidateVitalSignCommand(Integer heartRate
        ,Integer systolic,Integer diastolic,
        Double temperature,Integer oxygenSaturation
        ,Integer respiratoryRate) {
}
