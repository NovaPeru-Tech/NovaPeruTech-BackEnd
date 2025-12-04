package com.novaperutech.veyra.platform.health.application.internal.eventhandlers;

import com.novaperutech.veyra.platform.health.domain.model.commands.ValidateVitalSignCommand;
import com.novaperutech.veyra.platform.health.domain.services.VitalSignCommandService;
import com.novaperutech.veyra.platform.tracking.domain.model.events.MeasurementReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MeasurementEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementEventHandler.class);

    private final VitalSignCommandService vitalSignCommandService;

    public MeasurementEventHandler(VitalSignCommandService vitalSignCommandService) {
        this.vitalSignCommandService = vitalSignCommandService;
    }

    /**
     * Escucha mediciones de Tracking y delega validación al Command Service
     */
    @EventListener
    public void on(MeasurementReceivedEvent event) {
        LOGGER.debug("Received measurement from device {} (ID: {})",
                event.getDeviceId(), event.getMeasurementId());

        // Crear comando con datos del evento
        var command = new ValidateVitalSignCommand(
                event.getMeasurementId(),
                event.getDeviceId(),
                event.getHeartRate(),
                event.getSystolic(),
                event.getDiastolic(),
                event.getTemperature(),
                event.getOxygenSaturation(),
                event.getRespiratoryRate()
        );

        // Delegar al command service (donde está la lógica)
        vitalSignCommandService.handle(command);
    }
}
