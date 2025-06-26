package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.service.UserService;
import com.sch.service.ValidationService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	ValidationService validationService;
	@PostMapping("/register/super-admin")
	public ResponseEntity<?> registerSuperadmin(@RequestBody RegistrationDto registrationDto){
		Response<?> validationResponse = validationService.validateSuperadminPayload(registrationDto);
		if(validationResponse.getStatusCode()==HttpStatus.OK.value()) {
			Response<?> response=userService.registerSuperadmin(registrationDto);
			return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse,HttpStatus.valueOf(validationResponse.getStatusCode()));
	}
	
	@PostMapping("/register/client")
	public ResponseEntity<?> registerClient(@RequestBody RegistrationDto registrationDto){
			Response<?> response=userService.registerClient(registrationDto);
			return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam Long id, @RequestParam String newPassword) {
	Response<Object> resetPassword = userService.resetPassword(id, newPassword);
	return new ResponseEntity<>(resetPassword, HttpStatus.valueOf(resetPassword.getStatusCode()));

	}
}
