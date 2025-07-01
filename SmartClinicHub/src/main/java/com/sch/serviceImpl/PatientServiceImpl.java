package com.sch.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sch.dto.PatientDto;
import com.sch.dto.Response;
import com.sch.entity.Patient;
import com.sch.entity.User;
import com.sch.repository.PatientRepository;
import com.sch.repository.UserRepository;
import com.sch.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public Response<?> registerPatient(PatientDto patientDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("RECEPTIONIST"))) {
				if (patientDto.getId() == null) {
					Patient patient = new Patient();
					patient.setName(patientDto.getName());
					patient.setAge(patientDto.getAge());
					patient.setGender(patientDto.getGender());
					patient.setPhone(patientDto.getPhone());
					patient.setLanguagePref(patientDto.getLanguagePref());
					patient.setCreatedAt(new Date());
					Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
					if (optionalUser.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "User Not found", null);
					}
					patient.setClinic(optionalUser.get().getClinic());
					patientRepository.save(patient);
					return new Response<>(HttpStatus.OK.value(), "Patient registered successfully", null);
				}
				Optional<Patient> optionalPatient = patientRepository.findById(patientDto.getId());
				if (optionalPatient.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Id not found", null);
				}
				Patient patient = optionalPatient.get();
				patient.setName(patientDto.getName());
				patient.setAge(patientDto.getAge());
				patient.setGender(patientDto.getGender());
				patient.setPhone(patientDto.getPhone());
				patient.setLanguagePref(patientDto.getLanguagePref());
				patient.setCreatedAt(new Date());
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "User Not found", null);
				}
				patient.setClinic(optionalUser.get().getClinic());
				patientRepository.save(patient);
				return new Response<>(HttpStatus.OK.value(), "Patient updated successfully", null);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null);
	}
		
		
	}

	@Override
	public Response<?> getPatientByClinic() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("RECEPTIONIST"))) {
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if(optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "User Not found", null);
				}
				User user = optionalUser.get();
				List<Patient> patients= patientRepository.findAllByClinic(user.getClinic());
				if(patients.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(),"No patient found",null);
				}
				List<PatientDto> patient=patients.stream().map(Patient::convertToDto).toList();
				return new Response<>(HttpStatus.OK.value(), "Success", patient);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null);
		}
	}
}
