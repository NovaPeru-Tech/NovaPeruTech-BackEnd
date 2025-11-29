package com.novaperutech.veyra.platform.tracking.application.internal.commandservices;

import com.novaperutech.veyra.platform.tracking.domain.model.aggregates.Measurement;
import com.novaperutech.veyra.platform.tracking.domain.model.commands.SeedMeasurementCommand;
import com.novaperutech.veyra.platform.tracking.domain.model.valueobjects.*;
import com.novaperutech.veyra.platform.tracking.domain.services.MeasurementCommandService;
import com.novaperutech.veyra.platform.tracking.infrastructure.persistence.jpa.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of {@link MeasurementCommandService} to handle {@link SeedMeasurementCommand}
 */
@Service
public class MeasurementCommandServiceImpl implements MeasurementCommandService {

    private final MeasurementRepository measurementRepository;

    /**
     * Constructor for MeasurementCommandServiceImpl.
     * @param measurementRepository the measurement repository
     */
    public MeasurementCommandServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    /**
     * This method will handle the {@link SeedMeasurementCommand} and will create measurements if not exists
     * @param command {@link SeedMeasurementCommand}
     * @see SeedMeasurementCommand
     */
    @Override
    public void handle(SeedMeasurementCommand command) {
        if (measurementRepository.count() > 0) {
            return;
        }

        var measurements = generateMeasurements();
        measurementRepository.saveAll(measurements);
    }

    private List<Measurement> generateMeasurements() {
        var measurements = new ArrayList<Measurement>();
        var random = new Random();
        var totalResidents = 10;
        var measurementsPerResident = 24;

        for (long residentId = 1; residentId <= totalResidents; residentId++) {
            var deviceId = String.format("BAND_%03d", residentId);

            for (int hour = 0; hour < measurementsPerResident; hour++) {
                var timestamp = LocalDateTime.now().minusHours(hour);
                var generateAbnormal = (hour % 6 == 0);

                var measurement = createMeasurement(
                        residentId,
                        deviceId,
                        timestamp,
                        generateAbnormal,
                        random
                );

                measurements.add(measurement);
            }
        }

        return measurements;
    }

    private Measurement createMeasurement(
            Long residentId,
            String deviceId,
            LocalDateTime timestamp,
            boolean abnormal,
            Random random) {

        var heartRate = generateHeartRate(abnormal, random);
        var systolic = generateSystolic(abnormal, random);
        var diastolic = generateDiastolic(abnormal, random);

        if (systolic <= diastolic) {
            systolic = diastolic + 20;
        }

        var temperature = generateTemperature(abnormal, random);
        var oxygenSaturation = generateOxygenSaturation(abnormal, random);
        var respiratoryRate = generateRespiratoryRate(abnormal, random);

        return new Measurement(
                new ResidentId(residentId),
                new DeviceId(deviceId),
                timestamp,
                new HeartRate(heartRate),
                new BloodPressure(systolic, diastolic),
                new Temperature(temperature),
                new OxygenSaturation(oxygenSaturation),
                new RespiratoryRate(respiratoryRate)
        );
    }

    private int generateHeartRate(boolean abnormal, Random random) {
        var minNormal = 60;
        var rangeNormal = 30;
        var minAbnormalLow = 45;
        var rangeAbnormalLow = 5;
        var minAbnormalHigh = 110;
        var rangeAbnormalHigh = 15;

        return abnormal
                ? (random.nextBoolean() ? minAbnormalLow + random.nextInt(rangeAbnormalLow) : minAbnormalHigh + random.nextInt(rangeAbnormalHigh))
                : minNormal + random.nextInt(rangeNormal);
    }

    private int generateSystolic(boolean abnormal, Random random) {
        var minNormal = 110;
        var rangeNormal = 30;
        var minAbnormalLow = 85;
        var rangeAbnormalLow = 10;
        var minAbnormalHigh = 160;
        var rangeAbnormalHigh = 20;

        return abnormal
                ? (random.nextBoolean() ? minAbnormalHigh + random.nextInt(rangeAbnormalHigh) : minAbnormalLow + random.nextInt(rangeAbnormalLow))
                : minNormal + random.nextInt(rangeNormal);
    }

    private int generateDiastolic(boolean abnormal, Random random) {
        var minNormal = 70;
        var rangeNormal = 20;
        var minAbnormalLow = 50;
        var rangeAbnormalLow = 10;
        var minAbnormalHigh = 95;
        var rangeAbnormalHigh = 10;

        return abnormal
                ? (random.nextBoolean() ? minAbnormalHigh + random.nextInt(rangeAbnormalHigh) : minAbnormalLow + random.nextInt(rangeAbnormalLow))
                : minNormal + random.nextInt(rangeNormal);
    }

    private double generateTemperature(boolean abnormal, Random random) {
        var minNormal = 36.0;
        var rangeNormal = 1.2;
        var minAbnormalLow = 35.0;
        var rangeAbnormalLow = 0.5;
        var minAbnormalHigh = 38.5;

        var temp = abnormal
                ? (random.nextBoolean() ? minAbnormalHigh + random.nextDouble() : minAbnormalLow + random.nextDouble() * rangeAbnormalLow)
                : minNormal + random.nextDouble() * rangeNormal;

        return Math.round(temp * 10.0) / 10.0;
    }

    private int generateOxygenSaturation(boolean abnormal, Random random) {
        var minNormal = 95;
        var rangeNormal = 5;
        var minAbnormal = 82;
        var rangeAbnormal = 8;

        return abnormal
                ? minAbnormal + random.nextInt(rangeAbnormal)
                : minNormal + random.nextInt(rangeNormal);
    }

    private int generateRespiratoryRate(boolean abnormal, Random random) {
        var minNormal = 12;
        var rangeNormal = 8;
        var minAbnormalLow = 8;
        var rangeAbnormalLow = 3;
        var minAbnormalHigh = 24;
        var rangeAbnormalHigh = 6;

        return abnormal
                ? (random.nextBoolean() ? minAbnormalLow + random.nextInt(rangeAbnormalLow) : minAbnormalHigh + random.nextInt(rangeAbnormalHigh))
                : minNormal + random.nextInt(rangeNormal);
    }
}