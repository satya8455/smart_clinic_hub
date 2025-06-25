package com.sch.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sch.config.CustomizedUserDetailsService;
import com.sch.dto.LoginRequest;
import com.sch.dto.LoginResponseDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.dto.UserDto;
import com.sch.entity.Clinic;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.UserRepository;
import com.sch.service.JwtService;
import com.sch.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private CustomizedUserDetailsService customizedUserDetailsService;
	@Autowired
	private ClinicRepository clinicRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtServiceImpl jwtService;

	@Override
	public Response<?> registerSuperadmin(RegistrationDto registrationDto) {
		try {
			if (registrationDto.getId() == null) {
				User user = new User();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
				user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
				user.setCreatedAt(new Date());
				user.setIsActive(true);
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(), "Registration successfully", null);
			}
			Optional<User> optionalUser = userRepository.findById(registrationDto.getId());
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
				user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
				user.setUpdatedAt(new Date());
				user.setIsActive(true);
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(), "Updated successfully", null);
			}
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Id not found", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> registerClient(RegistrationDto registrationDto) {
		try {
			Optional<User> loggedUserOptional = customizedUserDetailsService.getUserDetails();

			if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().getRole().equals(Role.SUPER_ADMIN)) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You are not authorized to register a clinic.",
						null);
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
				clinic.setPhone(registrationDto.getClinicPhoneNo());
				clinic.setAddress(registrationDto.getAddress());
				clinic.setIsActive(true);
				clinic.setCreatedBy(loggedUser);
				clinic.setCreatedAt(new Date());
				Clinic savedClinic = clinicRepository.save(clinic);

				User admin = new User();
				admin.setName(registrationDto.getName());
				admin.setEmail(registrationDto.getEmail());
				admin.setPhone(registrationDto.getPhone());
				admin.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
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
			} else {
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
			e.printStackTrace(); // Or use log.error(...)
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.", null);
		}
	}

	@Override
	public Response<Object> generateToken(LoginRequest loginRequest) {
		try {
			Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

			if (userOptional.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid credentials.", null);
			}

			User user = userOptional.get();

			if (!user.getIsActive()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "User is deactivated.", null);
			}
			if (user.getClinic() != null && !user.getClinic().getIsActive()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic is deactivated.", null);
			}
			if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid credentials.", null);
			}

			if (!(user.getRole().equals(Role.SUPER_ADMIN) || user.getRole().equals(Role.ADMIN)
					|| user.getRole().equals(Role.DOCTOR) || user.getRole().equals(Role.RECEPTIONIST))) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Unauthorized role.", null);
			}

			String token = jwtService.generateToken(loginRequest.getEmail());
			LoginResponseDto response = new LoginResponseDto();
			response.setToken(token);
			response.setUserName(user.getName());
			response.setEmail(user.getEmail());
			response.setRoleType(user.getRole());
			response.setClinicId(user.getClinic() != null ? user.getClinic().getId() : null);

			return new Response<>(HttpStatus.OK.value(), "Login successful.", response);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.", null);
		}

	}

	@Override
	public Response<?> getAllAdmin() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SUPER_ADMIN"))) {
				List<User> list = userRepository.findAll();
				if (list.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "No data found", null);
				}
				List<UserDto> list1 = list.stream().filter(u -> u.getRole().equals(Role.ADMIN))														
						.map(User::convertToDto) 
						.toList();

				return new Response<>(HttpStatus.OK.value(), "Success.", list1);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.", null);
		}
	}

}
