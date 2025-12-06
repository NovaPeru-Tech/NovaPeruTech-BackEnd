package com.novaperutech.veyra.platform.health.domain.model.commands;

public record ValidateVitalSignCommand( Long measurementId,
                                        String deviceId,
                                        Integer heartRate,
                                        Integer systolic,
                                        Integer diastolic,
                                        Double temperature,
                                        Integer oxygenSaturation,
                                        Integer respiratoryRate){
}
