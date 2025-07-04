package com.sch.service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.PatientDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.dto.TokenDto;

public interface ValidationService {

	Response<?> validateSuperadminPayload(RegistrationDto registrationDto);

	Response<?> validateDepartmentCreationPayload(DepartmentDto departmentDto);

	Response<?> validatePatientRegistrationPayload(PatientDto patientDto);

	Response<?> validateTokenCreationPayload(TokenDto tokenDto);

	Response<?> validateClientRegisterPayload(RegistrationDto registrationDto);

}
