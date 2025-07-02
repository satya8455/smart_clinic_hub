package com.sch.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sch.entity.Subscription;
import com.sch.entity.User;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


	Optional<Subscription> findByClinicIdAndIsActiveTrue(Long clinicId);

	List<Subscription> findByEndDateAndIsActiveTrueAndReminderSentFalse(Date reminderDate);

	List<Subscription> findByIsActiveTrueAndEndDateBefore(Date today);

	// Use this to get last/latest subscription (active ya expired)
	@Query("SELECT s FROM Subscription s WHERE s.clinic.id = :clinicId ORDER BY s.endDate DESC")
	List<Subscription> findTopByClinicIdOrderByEndDateDesc(@Param("clinicId") Long clinicId);

}
