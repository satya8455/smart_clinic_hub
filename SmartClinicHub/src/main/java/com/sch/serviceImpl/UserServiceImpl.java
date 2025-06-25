package com.sch.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sch.config.CustomizedUserDetailsService;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.entity.Clinic;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.UserRepository;
import com.sch.service.UserService;
@Service
public class UserServiceImpl implements UserService{
@Autowired
UserRepository userRepository;

@Autowired
private CustomizedUserDetailsService customizedUserDetailsService;
@Autowired
private ClinicRepository clinicRepository;
@Autowired
private PasswordEncoder encoder;

	@Override
	public Response<?> registerSuperadmin(RegistrationDto registrationDto) {
		try {
			if(registrationDto.getId()==null) {
				User user=new User();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
	            user.setPassword(encoder.encode(registrationDto.getPassword()));
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
				user.setCreatedAt(new Date());
				user.setIsActive(true);
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(),"Registration successfully",null);
			}
			Optional<User> optionalUser=userRepository.findById(registrationDto.getId());
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
	            user.setPassword(encoder.encode(registrationDto.getPassword()));
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
				user.setUpdatedAt(new Date());
				user.setIsActive(true);
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(),"Updated successfully",null);
			}
			return new Response<>(HttpStatus.BAD_REQUEST.value(),"Id not found",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null);
		}
	}
	
	@Override
	public Response<?> registerClient(RegistrationDto registrationDto) {
	    try {
	        Optional<User> loggedUserOptional = customizedUserDetailsService.getUserDetails();
	        if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().getRole().equals(Role.SUPER_ADMIN)) {
	            return new Response<>(HttpStatus.BAD_REQUEST.value(), "You are not authorized to register a clinic.", null);
	        }
	        User loggedUser = loggedUserOptional.get();

	        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
	            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Admin user already exists.", null);
	        }

	        if (registrationDto.getClinicId() == null) {

	            if (clinicRepository.findByEmail(registrationDto.getClinicEmail()).isPresent()) {
	                return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic already exists.", null);
	            }

	            Clinic clinic = new Clinic();
	            clinic.setName(registrationDto.getClinicName());
	            clinic.setEmail(registrationDto.getClinicEmail());
	            clinic.setPhone(registrationDto.getClinicPhoneNo());  // use correct field name
	            clinic.setAddress(registrationDto.getAddress());
	            clinic.setIsActive(true);
	            clinic.setCreatedBy(loggedUser);
	            clinic.setCreatedAt(new Date());
	            Clinic savedClinic = clinicRepository.save(clinic);

	            User admin = new User();
	            admin.setName(registrationDto.getName());
	            admin.setEmail(registrationDto.getEmail());
	            admin.setPhone(registrationDto.getPhone());
	            admin.setPassword(encoder.encode(registrationDto.getPassword()));
	            admin.setRole(Role.ADMIN);
	            admin.setClinic(savedClinic);  
	            admin.setIsActive(true);
	            admin.setCreatedAt(new Date());
	            admin.setCreatedBy(loggedUser);
	            User savedAdmin = userRepository.save(admin);

	         
	            Map<String, Object> data = new HashMap<>();
	            data.put("clinic", savedClinic);
	            data.put("adminUser", savedAdmin);

	            return new Response<>(HttpStatus.OK.value(), "Clinic and Admin registered successfully.", data);
	        }
	        else {
	            Optional<Clinic> clinicOptional = clinicRepository.findById(registrationDto.getClinicId());
	            if (clinicOptional.isEmpty()) {
	                return new Response<>(HttpStatus.NOT_FOUND.value(), "Clinic not found.", null);
	            }

	            Clinic clinic = clinicOptional.get();
	            if (registrationDto.getClinicName() != null) {
	                clinic.setName(registrationDto.getClinicName());
	            }
	            if (registrationDto.getClinicEmail() != null) {
	                clinic.setEmail(registrationDto.getClinicEmail());
	            }
	            if (registrationDto.getClinicPhoneNo() != null) {
	                clinic.setPhone(registrationDto.getClinicPhoneNo());
	            }
	            if (registrationDto.getAddress() != null) {
	                clinic.setAddress(registrationDto.getAddress());
	            }

	            Clinic updatedClinic = clinicRepository.save(clinic);
	            return new Response<>(HttpStatus.OK.value(), "Clinic updated successfully.", updatedClinic);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();  // Or use log.error(...)
	        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.", null);
	    }
	}

}
