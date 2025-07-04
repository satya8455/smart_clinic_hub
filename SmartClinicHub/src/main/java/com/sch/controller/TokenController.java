package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;
import com.sch.entity.Token;
import com.sch.service.TokenService;
import com.sch.service.ValidationService;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	ValidationService validationService;

	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/create")
	public ResponseEntity<?> generateToken(@RequestBody TokenDto dto) {

		Response<?> response = validationService.validateToken(dto);
		if (response.getStatusCode() != 200) {
			return new ResponseEntity<>(response.getData(), HttpStatus.BAD_REQUEST);
		}
		response = tokenService.createToken(dto);
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response.getData(), HttpStatus.OK)
				: new ResponseEntity<>(response.getData(), HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllToken() {
		
		Response<?> response = tokenService.getAllToken();
		
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response.getData(), HttpStatus.OK)
				: new ResponseEntity<>(response.getData(), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/next")
	public ResponseEntity<?> getNext(){
		Token nextToken = tokenService.startNextToken();
		return nextToken != null ? new ResponseEntity<>(nextToken, HttpStatus.OK)
				: new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

	}

}
