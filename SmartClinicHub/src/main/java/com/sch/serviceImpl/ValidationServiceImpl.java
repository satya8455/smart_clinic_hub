package com.sch.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.service.ValidationService;
@Service
public class ValidationServiceImpl implements ValidationService{
	@Override
	public Response<?> validateSuperadminPayload(RegistrationDto registrationDto){
		if(registrationDto==null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"RegistraionDto should not be null",null);
		}
		if(registrationDto.getName()==null || registrationDto.getName().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide user name",null);
		}
		if(registrationDto.getEmail()==null || registrationDto.getEmail().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide user email",null);
		}
		if(registrationDto.getPassword()==null || registrationDto.getPassword().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide password",null);
		}
		if(registrationDto.getPhone()==null || registrationDto.getPhone().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide phone number",null);
		}
		return new Response<>(HttpStatus.OK.value(), "Success.", null);
	}
}
