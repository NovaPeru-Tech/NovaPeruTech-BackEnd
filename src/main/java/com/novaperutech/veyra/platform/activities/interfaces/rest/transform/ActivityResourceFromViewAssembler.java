package com.novaperutech.veyra.platform.activities.interfaces.rest.transform;

import com.novaperutech.veyra.platform.activities.domain.model.valueobjects.ActivityView;
import com.novaperutech.veyra.platform.activities.interfaces.rest.resources.ActivityResource;
import java.time.format.DateTimeFormatter;

public class ActivityResourceFromViewAssembler {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ActivityResource toResourceFromView(ActivityView view) {
        String hourRange = String.format("%s-%s",
                view.getStartTime().format(TIME_FORMATTER),
                view.getEndTime().format(TIME_FORMATTER)
        );

        // Mapea al ActivityResource que tu frontend espera
        return new ActivityResource(
                view.getActivityId(),
                hourRange,
                view.getAttendantName(),
                view.getActivityName(),
                view.getArea(),
                view.getStatus().toString()
        );
    }
}
