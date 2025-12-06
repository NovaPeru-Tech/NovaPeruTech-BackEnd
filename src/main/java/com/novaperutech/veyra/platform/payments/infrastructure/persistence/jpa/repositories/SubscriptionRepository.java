package com.novaperutech.veyra.platform.payments.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.payments.domain.model.aggregates.Subscription;
import com.novaperutech.veyra.platform.payments.domain.model.valueobjects.SubscriptionStatus;
import com.novaperutech.veyra.platform.payments.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);

    List<Subscription> findByUserId(UserId userId);

    Optional<Subscription> findByUserIdAndStatus(UserId userId, SubscriptionStatus status);

    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId AND s.status = 'ACTIVE'")
    Optional<Subscription> findActiveSubscriptionByUserId(@Param("userId") Long userId);
    @Query("SELECT s FROM Subscription s WHERE s.status = 'ACTIVE' AND s.currentPeriodEnd BETWEEN :start AND :end")
    List<Subscription> findSubscriptionsExpiringBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    boolean existsByUserIdAndStatus(UserId userId, SubscriptionStatus status);
    List<Subscription> findByStatus(SubscriptionStatus status);
}