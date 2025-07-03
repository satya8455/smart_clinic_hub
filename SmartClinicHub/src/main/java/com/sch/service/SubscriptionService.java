package com.sch.service;

import com.sch.dto.Response;
import com.sch.dto.SubscriptionPlanDto;

public interface SubscriptionService {

	Response<?> createplan(SubscriptionPlanDto planDto);
	public boolean isClinicSubscriptionActive(Long clinicId);
	Response<?> upgradePlan(SubscriptionPlanDto planDto);
	Response<?> getAllPlan();
}
