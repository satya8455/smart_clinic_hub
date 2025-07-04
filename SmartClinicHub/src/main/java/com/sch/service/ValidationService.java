package com.sch.service;

import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.dto.TokenDto;

public interface ValidationService {

	Response<?> validateSuperadminPayload(RegistrationDto registrationDto);

	Response<?> validateToken(TokenDto dto);

}
