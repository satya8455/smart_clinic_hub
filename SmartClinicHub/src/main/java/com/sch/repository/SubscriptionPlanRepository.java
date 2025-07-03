package com.sch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.SubscriptionPlan;
import com.sch.entity.User;
import com.sch.enums.PlanType;
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
	
    Optional<SubscriptionPlan> findByName(PlanType name);

}
