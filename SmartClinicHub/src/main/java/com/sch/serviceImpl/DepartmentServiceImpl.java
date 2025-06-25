package com.sch.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;
import com.sch.entity.Department;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.DepartmentRepository;
import com.sch.repository.UserRepository;
import com.sch.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService{
@Autowired
DepartmentRepository departmentRepository;
@Autowired
UserRepository userRepository;
	@Override
	public Response<?> createDepartment(DepartmentDto departmentDto) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(departmentDto.getId()==null) {
			Department department=new Department();
			department.setName(departmentDto.getName());
			department.setCreatedAt(new Date());
			Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
			if(optionalUser.isPresent() && optionalUser.get().getRole().equals(Role.ADMIN)) {
				department.setCreatedBy(optionalUser.get());
			}
		}
//		Optional<Department> optional = departmentRepository.findById(departmentDto.getId());
		return null;
		
	}

}
