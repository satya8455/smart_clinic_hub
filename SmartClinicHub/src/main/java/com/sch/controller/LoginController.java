package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.LoginRequest;
import com.sch.dto.Response;
import com.sch.service.UserService;


@RestController
public class LoginController {

//	@Autowired
//	private ValidatorService validatorService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//		Response<Object> validationResponse = validatorService.checkForLoginPayload(loginRequest);
//		if (validationResponse.getResponseCode() == HttpStatus.OK.value()) {
			 Response<Object> response = userService.generateToken(loginRequest);
			return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
//		}
//		return new ResponseEntity<>(validationResponse, HttpStatus.valueOf(validationResponse.getResponseCode()));
	}
}