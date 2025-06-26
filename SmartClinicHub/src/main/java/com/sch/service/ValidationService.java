package com.sch.service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;

public interface ValidationService {

	Response<?> validateSuperadminPayload(RegistrationDto registrationDto);

	Response<?> validateDepartmentCreationPayload(DepartmentDto departmentDto);

}
