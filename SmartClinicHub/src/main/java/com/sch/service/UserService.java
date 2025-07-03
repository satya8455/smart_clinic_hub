package com.sch.service;

import com.sch.dto.LoginRequest;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.enums.Role;

public interface UserService {

	Response<?> registerSuperadmin(RegistrationDto registrationDto);

	Response<?> registerClient(RegistrationDto registrationDto);

	Response<Object> generateToken(LoginRequest loginRequest);

	Response<Object> resetPassword(Long id, String newPassword);

	Response<?> getAllAdmin();

	Response<?> registerStaff(RegistrationDto registrationDto);

	Response<?> filterUser(Role role, Long clinicId);

	Response<?> mentionAvailabilityOfDoctor(Long doctorId);

	Response<?> getAllDoctor();
	
	Response<?> getDoctorById(Long id);

}