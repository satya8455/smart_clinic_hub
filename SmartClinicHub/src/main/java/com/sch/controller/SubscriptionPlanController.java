package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.PatientDto;
import com.sch.dto.Response;
import com.sch.dto.SubscriptionPlanDto;
import com.sch.service.SubscriptionService;

@RestController
public class SubscriptionPlanController {
	@Autowired
	private SubscriptionService subscriptionService;
	@PostMapping("/api/create/plan")
	public ResponseEntity<?> createPlan(@RequestBody SubscriptionPlanDto planDto){
		Response<?> response=subscriptionService.createplan(planDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
}
	
	
	@PostMapping("/api/upgrade/plan")
	public ResponseEntity<?> upgradePlan(@RequestBody SubscriptionPlanDto planDto){
		Response<?> response=subscriptionService.upgradePlan(planDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
}
	
	
}
