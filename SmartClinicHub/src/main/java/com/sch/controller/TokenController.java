package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;
import com.sch.service.TokenService;
import com.sch.service.ValidationService;

@RestController
public class TokenController {
	@Autowired
	TokenService tokenService;
	@Autowired
	ValidationService validationService;

	@PostMapping("/api/create/token")
	public ResponseEntity<?> createToken(@RequestBody TokenDto tokenDto) {
		Response<?> validationResponse = validationService.validateTokenCreationPayload(tokenDto);
		if (validationResponse.getStatusCode() == HttpStatus.OK.value()) {
			Response<?> response = tokenService.createToken(tokenDto);
			return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse, HttpStatus.valueOf(validationResponse.getStatusCode()));

	}
}
