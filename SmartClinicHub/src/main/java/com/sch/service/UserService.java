package com.sch.service;

import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;

public interface UserService {

	Response<?> registerSuperadmin(RegistrationDto registrationDto);

}
