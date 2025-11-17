package com.novaperutech.veyra.platform.activities.application.internal.queryservices;

import com.novaperutech.veyra.platform.activities.domain.model.aggregates.Activity;
import com.novaperutech.veyra.platform.activities.domain.model.queries.GetActivitiesByDateAndNursingHomeQuery;
import com.novaperutech.veyra.platform.activities.domain.model.valueobjects.ActivityView;
import com.novaperutech.veyra.platform.activities.domain.services.ActivityQueryService;
import com.novaperutech.veyra.platform.activities.infrastructure.persistence.jpa.repositories.ActivityRepository;
import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Resident;
import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.PersonProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;

    public ActivityQueryServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<ActivityView> handle(GetActivitiesByDateAndNursingHomeQuery query) {

        // 1. Llamamos a la consulta simple (la "otra forma")
        List<Activity> activities = activityRepository.findByActivityDateAndNursingHomeId_NursingHomeIdOrderByPeriod_StartTime(
                query.date(),
                query.nursingHomeId()
        );

        // 2. Mapeamos la lista.
        // ADVERTENCIA: Esto causará el problema N+1 (múltiples consultas)
        // porque por cada actividad, JPA tendrá que buscar el Residente,
        // el Staff y los PersonProfiles.
        return activities.stream()
                .map(this::mapActivityToActivityView)
                .collect(Collectors.toList());
    }

    /**
     * Este método de mapeo es el que activa el "Lazy Loading" (N+1 queries).
     * Obtiene los nombres de los objetos relacionados.
     */
    private ActivityView mapActivityToActivityView(Activity activity) {

        // Obtenemos los objetos relacionados (esto dispara las consultas)
        Resident resident = activity.getResident();
        Staff staff = activity.getStaff();

        // Asumimos que Resident y Staff tienen una relación (aunque sea lazy)
        // a PersonProfile, como en tu Agregado de Activity.
        // Si no la tienen, este código fallará.

        // NOTA: Tu código en develop (Staff.java y Resident.java) NO tiene
        // la relación directa a PersonProfile, solo el ID.
        // Para que este código funcione, DEBES usar la versión de
        // Activity.java que te di, que SÍ tiene las relaciones:
        // @ManyToOne private Resident resident;
        // @ManyToOne private Staff staff;
        // Y esos Agregados (Staff, Resident) DEBEN tener la relación
        // a PersonProfile.

        // Por ahora, asumiré que las relaciones existen:
        String residentName = "Residente " + resident.getId(); // Nombre de fallback
        String attendantName = "Staff " + staff.getId(); // Nombre de fallback

        // Si SÍ tienes la relación a PersonProfile en Staff y Resident:
        // residentName = resident.getPersonProfile().getPersonName().getFirstName();
        // attendantName = staff.getPersonProfile().getPersonName().getLastName();


        return new ActivityView(
                activity.getId(),
                activity.getName(),
                activity.getPeriod().getStartTime(),
                activity.getPeriod().getEndTime(),
                activity.getArea().getAreaCode(),
                activity.getStatus(),
                activity.getResidentId(),
                residentName,
                activity.getStaffMemberId(),
                attendantName
        );
    }
}