package com.novaperutech.veyra.platform.tracking.application.internal.eventhandlers;

import com.novaperutech.veyra.platform.tracking.domain.model.commands.SeedMeasurementCommand;
import com.novaperutech.veyra.platform.tracking.domain.services.MeasurementCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * ApplicationReadyEventHandler class
 * This class is used to handle the ApplicationReadyEvent for seeding measurements
 */
@Service
public class TrackingApplicationReadyEventHandler {
    private final MeasurementCommandService measurementCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingApplicationReadyEventHandler.class);

    /**
     * Constructor for ApplicationReadyEventHandler.
     * @param measurementCommandService the measurement command service
     */
    public TrackingApplicationReadyEventHandler(MeasurementCommandService measurementCommandService) {
        this.measurementCommandService = measurementCommandService;
    }

    /**
     * Handle the ApplicationReadyEvent
     * This method is used to seed the measurements
     * @param event the ApplicationReadyEvent to handle
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if measurements seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedMeasurementCommand = new SeedMeasurementCommand();
        measurementCommandService.handle(seedMeasurementCommand);
        LOGGER.info("Measurements seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}