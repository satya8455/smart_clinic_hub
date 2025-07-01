package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;
import com.sch.service.DepartmentService;
import com.sch.service.ValidationService;

@RestController
public class DepartmentController {
	@Autowired
	ValidationService validationService;
	@Autowired
	DepartmentService departmentService;
	@PostMapping("/api/create/department")
	public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto departmentDto){
		Response<?> validationResponse = validationService.validateDepartmentCreationPayload(departmentDto);
		if(validationResponse.getStatusCode()==HttpStatus.OK.value()) {
		Response<?> response= departmentService.createDepartment(departmentDto);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
		}
		return new ResponseEntity<>(validationResponse,HttpStatus.valueOf(validationResponse.getStatusCode()));
	}
	
	@GetMapping("/api/get/dept/by/clinic/id")
	public ResponseEntity<?> getDeptByClinicId(){
		Response<?> response=departmentService.getAllDeptByClinicId();
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@GetMapping("/api/delete/department")
	public ResponseEntity<?> deActivateDepartment(@RequestParam Long deptId){
		Response<?> response=departmentService.deActivateDepartment(deptId);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
}