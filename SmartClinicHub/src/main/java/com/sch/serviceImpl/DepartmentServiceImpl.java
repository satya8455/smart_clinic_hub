package com.sch.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sch.config.CustomizedUserDetailsService;
import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;
import com.sch.entity.Department;
import com.sch.entity.User;
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

	@Autowired
	private CustomizedUserDetailsService customizedUserDetailsService;

	@Override
	public Response<?> createDepartment(DepartmentDto departmentDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {

				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.UNAUTHORIZED.value(), "User not found", null);
				}

				User loggedInUser = optionalUser.get();
				Optional<Department> optionalDept = departmentRepository
						.findByNameIgnoreCaseAndClinic(departmentDto.getName(), loggedInUser.getClinic());
				Department department;
				if (optionalDept.isEmpty()) {
					department = new Department();
					department.setCreatedAt(new Date());
					department.setCreatedBy(loggedInUser);
					department.setClinic(loggedInUser.getClinic());
					department.setName(departmentDto.getName());
					department.setIsActive(true);

					departmentRepository.save(department);
					return new Response<>(HttpStatus.OK.value(), "Department created successfully", null);
				}

			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> deActivateDepartment(Long deptId) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if(optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), " User Not found", null);
				}
				User user = optionalUser.get();
				if(user.getClinic()==null) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), " Clinic Not found", null);
				}
				Optional<Department> optionalDept = departmentRepository.findByClinicAndId(user.getClinic(), deptId);
				if (optionalDept.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Not found", null);
				}
				Department department = optionalDept.get();
				department.setIsActive(false);
				if(!department.getIsActive()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(),"Already deactivated",null);
				}
				departmentRepository.save(department);
				return new Response<>(HttpStatus.OK.value(), "Department deactivated successfully", null);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}


	@Override
	public Response<?> getAllDeptByClinicId() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if(optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), " User Not found", null);
				}
				User user = optionalUser.get();
				if(user.getClinic()==null) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), " Clinic Not found", null);
				}
				List<Department> depts=departmentRepository.findAllByClinic(user.getClinic());
				if(depts.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), " Departments Not found", null);
				}
				List<DepartmentDto> dept=depts.stream().map(Department::convertToDto).toList();
				return new Response<>(HttpStatus.OK.value(), " Success", dept);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);

		}
	}

}