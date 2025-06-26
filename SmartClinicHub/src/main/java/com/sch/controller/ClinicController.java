package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.Response;
import com.sch.service.ClinicService;

@RestController
@RequestMapping("clinic")
public class ClinicController {
	@Autowired
	ClinicService clinicService;
	@GetMapping("/get/by/id")
	public ResponseEntity<?> getClinicById(@RequestParam Long id){
		Response<?> response= clinicService.getClinicById(id);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
	@GetMapping("/get/all")
	public ResponseEntity<?> getAllClinic(){
		Response<?> response= clinicService.getAllClinic();
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
}
