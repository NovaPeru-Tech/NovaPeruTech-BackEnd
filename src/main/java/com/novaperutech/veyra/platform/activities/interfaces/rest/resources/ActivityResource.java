package com.novaperutech.veyra.platform.activities.interfaces.rest.resources;

// Este recurso coincide con tu frontend
public record ActivityResource(
        Long activityId,
        String hour, // "08:00-09:00"
        String attendantName, // "Martínez"
        String activityName, // "Breakfast"
        String areaToDevelop, // "M453"
        String status
) {}

