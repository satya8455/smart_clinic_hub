package com.sch.service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.Response;

public interface DepartmentService {

	Response<?> createDepartment(DepartmentDto departmentDto);

	Response<?> deActivateDepartment( Long deptId);

	Response<?> getAllDeptByClinicId();

}