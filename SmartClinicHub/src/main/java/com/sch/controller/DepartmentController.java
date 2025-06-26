package com.sch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;
import com.sch.service.DepartmentService;

@RestController
@RequestMapping("department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	@PostMapping("/create")
	public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto departmentDto){
		Response<?> response= departmentService.createDepartment(departmentDto);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
	}
}
