package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.DoctorDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.enums.Role;
import com.sch.service.UserService;
import com.sch.service.ValidationService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	ValidationService validationService;

	@PostMapping("/register/super-admin")
	public ResponseEntity<?> registerSuperadmin(@RequestBody RegistrationDto registrationDto) {
		Response<?> validationResponse = validationService.validateSuperadminPayload(registrationDto);
		if (validationResponse.getStatusCode() == HttpStatus.OK.value()) {
			Response<?> response = userService.registerSuperadmin(registrationDto);
			return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse, HttpStatus.valueOf(validationResponse.getStatusCode()));
	}

	@PostMapping("/api/register/client")
	public ResponseEntity<?> registerClient(@RequestBody RegistrationDto registrationDto) {
		Response<?> validationResponse = validationService.validateClientRegisterPayload(registrationDto);
		if (validationResponse.getStatusCode() == HttpStatus.OK.value()) {
			Response<?> response = userService.registerClient(registrationDto);
			return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse, HttpStatus.valueOf(validationResponse.getStatusCode()));
	}

	@GetMapping("/api/get/all/admin")
	public ResponseEntity<?> getAllAdmin() {
		Response<?> response = userService.getAllAdmin();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam Long id, @RequestParam String newPassword) {
		Response<Object> resetPassword = userService.resetPassword(id, newPassword);
		return new ResponseEntity<>(resetPassword, HttpStatus.valueOf(resetPassword.getStatusCode()));
	}

	@PostMapping("/api/register/staff")
	public ResponseEntity<?> registerStaff(@RequestBody DoctorDto registrationDto) {
		Response<?> response = userService.registerStaff(registrationDto);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@GetMapping("/api/filter/user")
	public ResponseEntity<?> filterUser(@RequestParam Role role, @RequestParam(required = false) Long clinicId) {
		Response<?> response = userService.filterUser(role, clinicId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@GetMapping("/update/availability/of/doctor")
	public ResponseEntity<?> updateAvailabilityOfDoctor(@RequestParam Long doctorId) {
		Response<?> response = userService.mentionAvailabilityOfDoctor(doctorId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@GetMapping("/api/get/all/doctor")
	public ResponseEntity<?> getAllDoctor() {
		Response<?> response = userService.getAllDoctor();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@GetMapping("/api/get/doctor/by/id")
	public ResponseEntity<?> getDoctorById(@RequestParam Long id) {
		Response<?> response = userService.getDoctorById(id);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

}