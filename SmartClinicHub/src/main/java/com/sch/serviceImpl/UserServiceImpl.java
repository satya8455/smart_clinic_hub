
package com.sch.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.usertype.LoggableUserType;
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
import com.sch.entity.Department;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.DepartmentRepository;
import com.sch.repository.UserRepository;
import com.sch.service.EmailService;
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

	@Autowired
	private EmailService emailService;

	@Autowired
	DepartmentRepository departmentRepository;

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
				admin.setPassword(passwordEncoder.encode("Rst@123"));
				admin.setRole(Role.ADMIN);
				admin.setClinic(savedClinic);
				admin.setIsActive(true);
				admin.setCreatedAt(new Date());
				admin.setCreatedBy(loggedUser);
				User savedAdmin = userRepository.save(admin);
				emailService.sendPasswordResetEmail(savedAdmin);

				return new Response<>(HttpStatus.OK.value(), "Clinic and Admin registered successfully.", null);
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
	public Response<Object> resetPassword(Long id, String newPassword) {
		try {

			Optional<User> optionalUser = userRepository.findById(id);
			if (optionalUser.isPresent()) {

				User user = optionalUser.get();
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(), "password reset Successfully", null);
			}
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User doesn't exist", null);

		} catch (Exception e) {
			// TODO: handle exception
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Invalid reset request.", null);
		}

	}

	public Response<?> getAllAdmin() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SUPER_ADMIN"))) {
				List<User> list = userRepository.findAll();
				if (list.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "No data found", null);
				}
				List<UserDto> list1 = list.stream().filter(u -> u.getRole().equals(Role.ADMIN)).map(User::convertToDto)
						.toList();

				return new Response<>(HttpStatus.OK.value(), "Success.", list1);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong.", null);
		}
	}

	@Override
	public Response<?> registerStaff(RegistrationDto registrationDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {

				if (registrationDto.getId() == null) {
					User user = new User();
					user.setName(registrationDto.getName());
					user.setEmail(registrationDto.getEmail());
					user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
					user.setPhone(registrationDto.getPhone());

					Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
					if (optionalUser.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "User admin not found", null);
					}

					user.setRole(registrationDto.getRole());
					user.setCreatedBy(optionalUser.get());
					user.setCreatedAt(new Date());
					user.setIsActive(true);
					if (optionalUser.get().getClinic() == null) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic not found", null);
					}
					Clinic clinic = optionalUser.get().getClinic();
					user.setClinic(clinic);
					if (registrationDto.getRole().equals(Role.DOCTOR)) {
						Optional<Department> optionalDept = departmentRepository
								.findByIdAndClinic(registrationDto.getDepartmentId(), clinic);
						if (optionalDept.isEmpty()) {
							return new Response<>(HttpStatus.BAD_REQUEST.value(), "Department doesn't exists", null);
						}
						user.setDepartment(optionalDept.get());
					}

					userRepository.save(user);
					return new Response<>(HttpStatus.OK.value(), "Registration successful", null);
				}
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> filterUser(Role role, Long clinicId) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			List<User> lists = new ArrayList<>();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SUPER_ADMIN"))) {
				if (role == null || clinicId == null) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Provide valid role and clinic id", null);
				}
				lists = userRepository.findAllByRoleAndClinicId(role, clinicId);
			} else {
				if (role == null || role.equals(Role.SUPER_ADMIN)) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Provide valid role", null);
				}
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
				}
				User user = optionalUser.get();
				lists = userRepository.findAllByRoleAndClinicId(role, user.getClinic().getId());
			}
			if (lists.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "No data found", null);
			}
			List<UserDto> list = lists.stream().map(User::convertToDto).toList();
			return new Response<>(HttpStatus.OK.value(), "Success", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> mentionAvailabilityOfDoctor(Long doctorId) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("RECEPTIONIST"))) {

				Optional<User> optionalUser = userRepository.findById(doctorId);
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
				}
				User user = optionalUser.get();
				LocalTime currentTime = LocalTime.of(9, 0);
				user.setAvailableFrom(currentTime);
				user.setAvailableTo(currentTime.plusHours(3));
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(), "Success", null);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> getAllDoctor() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SUPER_ADMIN"))) {

				List<User> listOfDoctors = userRepository.findAll();
				if (listOfDoctors.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Data not found", null);
				}
				List<UserDto> doctors = listOfDoctors.stream().filter(r -> r.getRole().equals(Role.DOCTOR))
						.map(User::convertToDto).toList();
				return new Response<>(HttpStatus.OK.value(), "Success", doctors);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> getDoctorById(Long id) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("RECEPTIONIST"))) {

				Optional<User> doctors = userRepository.findById(id);
				if (doctors.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Users not found", null);
				}
				User doctorUser = doctors.get();
				if (doctorUser.getRole().equals(Role.DOCTOR)) {
					return new Response<>(HttpStatus.OK.value(), "Success", doctorUser);
				}
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Doctor not found", null);
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

}