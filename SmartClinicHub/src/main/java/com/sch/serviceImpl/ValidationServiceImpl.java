package com.sch.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sch.dto.DepartmentDto;
import com.sch.dto.PatientDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.dto.TokenDto;
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
	
	@Override
	public Response<?> validateDepartmentCreationPayload(DepartmentDto departmentDto){
		if(departmentDto==null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"DepartmentDto should not be null",null);
		}
		if(departmentDto.getName()==null || departmentDto.getName().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide department name",null);
		}
		return new Response<>(HttpStatus.OK.value(), "Success.", null);
	}
	
	@Override
	public Response<?> validatePatientRegistrationPayload(PatientDto patientDto){
		if(patientDto==null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"PatientDto should not be null",null);
		}
		if(patientDto.getName()==null || patientDto.getName().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide Patient name",null);
		}
		if(patientDto.getAge()==null || patientDto.getAge()<=0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid age",null);
		}
		if(patientDto.getGender()==null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Gender should be provided",null);
		}
		if(patientDto.getLanguagePref()==null || patientDto.getLanguagePref().isEmpty()) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide prefered language",null);
		}
		if(patientDto.getPhone()==null || patientDto.getPhone().isEmpty() || !patientDto.getPhone().matches("^(\\+91)?\\d{10}$")) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid phone number",null);
		}
		return new Response<>(HttpStatus.OK.value(), "Success.", null);
	}
	@Override
	public Response<?> validateTokenCreationPayload(TokenDto tokenDto){
		if(tokenDto==null) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"TokenDto should not be null",null);
		}
		if(tokenDto.getStatus()==null ) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid status",null);
		}
		if(tokenDto.getDepartmentId()==null || tokenDto.getDepartmentId()<=0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid department id",null);
		}
		if(tokenDto.getDoctorId()==null || tokenDto.getDoctorId()<=0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid doctor id",null);
		}
		if(tokenDto.getPatientId()==null || tokenDto.getPatientId()<=0) {
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Provide valid patient id",null);
		}
		return new Response<>(HttpStatus.OK.value(), "Success.", null);
	}
}
