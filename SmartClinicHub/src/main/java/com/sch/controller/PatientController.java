package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.PatientDto;
import com.sch.dto.Response;
import com.sch.service.PatientService;
import com.sch.service.ValidationService;

@RestController
public class PatientController {
	@Autowired
	PatientService patientService;
	@Autowired
	ValidationService validationService;
	
	@PostMapping("/api/register/patient")
	public ResponseEntity<?> registerPatient(@RequestBody PatientDto patientDto){
		Response<?> validationResponse = validationService.validatePatientRegistrationPayload(patientDto);
		if(validationResponse.getStatusCode()==HttpStatus.OK.value()) {
		Response<?> response=patientService.registerPatient(patientDto);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse,HttpStatus.valueOf(validationResponse.getStatusCode()));
	}
	
	@GetMapping("/api/get/all/patient")
	public ResponseEntity<?> getPatientByClinic(){
		Response<?> response=patientService.getPatientByClinic();
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
}

