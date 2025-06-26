package com.sch.service;

import com.sch.dto.LoginRequest;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;

public interface UserService {

	Response<?> registerSuperadmin(RegistrationDto registrationDto);

	Response<?> registerClient(RegistrationDto registrationDto);
	Response<Object> generateToken(LoginRequest loginRequest);
	Response<Object>resetPassword(Long id,String newPassword);

	Response<?> getAllAdmin();

}
