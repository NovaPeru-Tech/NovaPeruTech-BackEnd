package com.novaperutech.veyra.platform.analytics.infrastructure.persistence.jpa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AnalyticsReadRepository {

    @PersistenceContext
    private EntityManager em;

    /* -------------------- Helpers -------------------- */
    private long qlForLong(String sql, Object... params) {
        var q = em.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        Object r = q.getSingleResult();
        return r == null ? 0L : ((Number) r).longValue();
    }

    private double qlForDouble(String sql, Object... params) {
        var q = em.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        Object r = q.getSingleResult();
        return r == null ? 0.0 : ((Number) r).doubleValue();
    }

    private BigDecimal qlForBigDecimal(String sql, Object... params) {
        var q = em.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        Object r = q.getSingleResult();
        if (r == null) return BigDecimal.ZERO;
        if (r instanceof BigDecimal bd) return bd;
        return BigDecimal.valueOf(((Number) r).doubleValue());
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> qlForList(String sql, Object... params) {
        var q = em.createNativeQuery(sql);
        for (int i = 0; i < params.length; i++) q.setParameter(i + 1, params[i]);
        var raw = q.getResultList();
        // ensure List<Object[]>
        var out = new ArrayList<Object[]>();
        for (Object row : raw) out.add((Object[]) row);
        return out;
    }

    /* -------------------- Residents -------------------- */
    public long countResidents(Long nh) {
        // tabla en tus logs: residents
        return qlForLong("select count(*) from residents r where r.nursing_home_id = ?", nh);
    }

    public long countActiveResidents(Long nh) {
        return qlForLong("select count(*) from residents r where r.nursing_home_id = ? and r.status = 'ACTIVE'", nh);
    }

    public double averageResidentAge(Long nh) {
        return qlForDouble("""
            select coalesce(avg(timestampdiff(year, r.birth_date, current_date())),0)
            from residents r where r.nursing_home_id = ?
        """, nh);
    }

    public long countAdmissionsInMonth(Long nh, LocalDate start, LocalDate end) {
        return qlForLong("""
            select count(*) from residents r
            where r.nursing_home_id = ? and r.admission_date between ? and ?
        """, nh, start, end);
    }

    public double averageLengthOfStayDays(Long nh) {
        return qlForDouble("""
            select coalesce(avg(case when r.discharge_date is not null
                 then datediff(r.discharge_date, r.admission_date)
                 else datediff(current_date(), r.admission_date) end),0)
            from residents r where r.nursing_home_id = ?
        """, nh);
    }

    public List<Object[]> residentsByGender(Long nh) {
        return qlForList("""
            select r.gender, count(*) from residents r
            where r.nursing_home_id = ?
            group by r.gender
        """, nh);
    }

    public List<Object[]> residentsByAgeBucket(Long nh) {
        return qlForList("""
            select bucket, count(*) from (
              select case
                when timestampdiff(year, r.birth_date, current_date()) <= 17 then '0-17'
                when timestampdiff(year, r.birth_date, current_date()) between 18 and 39 then '18-39'
                when timestampdiff(year, r.birth_date, current_date()) between 40 and 64 then '40-64'
                when timestampdiff(year, r.birth_date, current_date()) between 65 and 79 then '65-79'
                else '80+' end as bucket
              from residents r where r.nursing_home_id = ?
            ) t group by bucket
        """, nh);
    }

    public List<Object[]> residentsByCondition(Long nh) {
        // ajusta nombres si tus tablas son distintas
        return qlForList("""
            select c.name, count(*) from resident_condition rc
            join condition c on c.id = rc.condition_id
            join residents r on r.id = rc.resident_id
            where r.nursing_home_id = ?
            group by c.name
        """, nh);
    }

    /* -------------------- Staff (empleados) -------------------- */
    public long countEmployees(Long nh) {
        return qlForLong("select count(*) from staff s where s.nursing_home_id = ?", nh);
    }

    public long countActiveEmployees(Long nh) {
        return qlForLong("select count(*) from staff s where s.nursing_home_id = ? and s.status = 'ACTIVE'", nh);
    }

    /* -------------------- Medications / Inventory -------------------- */
    public long countMedications(Long nh) {
        return qlForLong("select count(*) from medication m where m.nursing_home_id = ?", nh);
    }

    public BigDecimal sumInventoryValue(Long nh) {
        return qlForBigDecimal("select coalesce(sum(m.stock * m.unit_price),0) from medication m where m.nursing_home_id = ?", nh);
    }

    public long countLowStock(Long nh) {
        return qlForLong("select count(*) from medication m where m.nursing_home_id = ? and m.stock <= m.low_stock_threshold", nh);
    }

    public long countOutOfStock(Long nh) {
        return qlForLong("select count(*) from medication m where m.nursing_home_id = ? and m.stock = 0", nh);
    }

    public long countExpiringBetween(Long nh, LocalDate start, LocalDate end) {
        return qlForLong("""
            select count(*) from medication m
            where m.nursing_home_id = ? and m.expiration_date between ? and ?
        """, nh, start, end);
    }

    public List<Object[]> topMedicationsByUsage(Long nh) {
        return qlForList("""
            select m.id, m.name, count(a.id) as uses
            from administration a
            join medication m on m.id = a.medication_id
            where m.nursing_home_id = ?
            group by m.id, m.name
            order by uses desc
            limit 10
        """, nh);
    }

    /* -------------------- Activities -------------------- */
    public long countUpcomingActivities(Long nh, LocalDate from) {
        return qlForLong("select count(*) from activity a where a.nursing_home_id = ? and a.date >= ?", nh, from);
    }

    /* -------------------- Beds / Occupancy -------------------- */
    public long countBeds(Long nh) {
        return qlForLong("select count(*) from bed b where b.nursing_home_id = ?", nh);
    }

    public long countOccupiedBeds(Long nh) {
        return qlForLong("select count(*) from bed b where b.nursing_home_id = ? and b.occupied = true", nh);
    }
}
