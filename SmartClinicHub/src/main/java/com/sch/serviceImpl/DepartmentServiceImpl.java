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
	public Response<?> createDepartment(DepartmentDto departmentDto) {try {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
	        return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
	    }

	    Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
	    if (optionalUser.isEmpty()) {
	        return new Response<>(HttpStatus.UNAUTHORIZED.value(), "User not found", null);
	    }

	    User loggedInUser = optionalUser.get();

	    Optional<Clinic> optionalClinic = clinicRepository.findById(departmentDto.getClinicId());
	    if (optionalClinic.isEmpty()) {
	        return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic not found", null);
	    }

	    Clinic clinic = optionalClinic.get();

	    // Ensure that the Admin is assigned to this clinic
	    if (!clinic.equals(loggedInUser.getClinic())) {
	        return new Response<>(HttpStatus.BAD_REQUEST.value(), "Admin is not assigned to this clinic", null);
	    }

	    Department department;
	    if (departmentDto.getId() == null) {
	        // Create new department
	        department = new Department();
	        department.setCreatedAt(new Date());
	        department.setCreatedBy(loggedInUser);
	        department.setClinic(clinic);
	        department.setName(departmentDto.getName());

	        departmentRepository.save(department);
	        return new Response<>(HttpStatus.OK.value(), "Department created successfully", null);
	    } else {
	        // Update existing department
	        Optional<Department> optionalDept = departmentRepository.findById(departmentDto.getId());
	        if (optionalDept.isEmpty()) {
	            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Department not found", null);
	        }

	        department = optionalDept.get();
	        department.setName(departmentDto.getName());
	        department.setClinic(clinic);
	        department.setCreatedAt(new Date());
	        department.setCreatedBy(loggedInUser);

	        departmentRepository.save(department);
	        return new Response<>(HttpStatus.OK.value(), "Department updated successfully", null);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
	}
}

}
