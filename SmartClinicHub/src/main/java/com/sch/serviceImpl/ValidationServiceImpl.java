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
		if(registrationDto.getEmail()==null || registrationDto.getEmail().isEmpty() || 
				!registrationDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid user email id",null);
		}
		if(registrationDto.getPassword()==null || registrationDto.getPassword().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide password",null);
		}
		if(registrationDto.getPhone()==null || registrationDto.getPhone().isEmpty()|| !registrationDto.getPhone().matches("^(\\+91)?\\d{10}$")) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide phone number",null);
		}
		return new Response<>(HttpStatus.OK.value(), "Success.", null);
	}
}
