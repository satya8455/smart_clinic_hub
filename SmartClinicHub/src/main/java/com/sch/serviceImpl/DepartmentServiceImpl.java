package com.sch.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;
import com.sch.entity.Clinic;
import com.sch.entity.Department;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.DepartmentRepository;
import com.sch.repository.UserRepository;
import com.sch.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClinicRepository clinicRepository;

	@Override
	public Response<?> createDepartment(DepartmentDto departmentDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
				if (departmentDto.getId() == null) {
					Department department = new Department();
					department.setName(departmentDto.getName());
					department.setCreatedAt(new Date());
					Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
					if (optionalUser.isEmpty()) {
						return new Response<>(HttpStatus.UNAUTHORIZED.value(), "User Not found", null);
					}
					Optional<Clinic> optionalClinic = clinicRepository.findById(departmentDto.getClinic());
					if (optionalClinic.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic not found", null);
					}
					Optional<User> optionalData=userRepository.findByRoleAndClinic(optionalUser.get().getRole(),optionalClinic.get());
					if(optionalData.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value()," The Admin is not assigned to this clinic",null);
					}
					department.setCreatedBy(optionalData.get().getCreatedBy());
					department.setClinic(optionalData.get().getClinic());
					departmentRepository.save(department);
					return new Response<>(HttpStatus.OK.value(), "Department created successfully", null);
				}
				// update
				Optional<Department> optionalDept = departmentRepository.findById(departmentDto.getId());
				if (optionalDept.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic not found", null);
				}
				Department department = optionalDept.get();
				department.setName(departmentDto.getName());
				department.setCreatedAt(new Date());
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not found", null);
				}
				Optional<Clinic> optionalClinic = clinicRepository.findById(departmentDto.getClinic());
				if (optionalClinic.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic not found", null);
				}
				Optional<User> optionalData=userRepository.findByRoleAndClinic(optionalUser.get().getRole(),optionalClinic.get());
				if(optionalData.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value()," The Admin is not assigned to this clinic",null);
				}
				department.setCreatedBy(optionalUser.get());
				department.setClinic(optionalClinic.get());
				departmentRepository.save(department);
				return new Response<>(HttpStatus.OK.value(), "Department updated successfully", null);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}

	}

}
