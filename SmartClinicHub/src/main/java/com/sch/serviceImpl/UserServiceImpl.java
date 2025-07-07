
package com.sch.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
import com.sch.dto.DoctorDto;
import com.sch.dto.LoginRequest;
import com.sch.dto.LoginResponseDto;
import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.dto.UserDto;
import com.sch.entity.Clinic;
import com.sch.entity.Department;
import com.sch.entity.Subscription;
import com.sch.entity.SubscriptionPlan;
import com.sch.entity.User;
import com.sch.enums.PlanType;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.DepartmentRepository;
import com.sch.repository.SubscriptionPlanRepository;
import com.sch.repository.SubscriptionRepository;
import com.sch.repository.UserRepository;
import com.sch.service.EmailService;
import com.sch.service.SubscriptionService;
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

	@Autowired
	private SubscriptionPlanRepository planRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private SubscriptionService subscriptionService;

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
			if (registrationDto == null) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Data can't be null", null);
			}

			Optional<User> loggedUserOptional = customizedUserDetailsService.getUserDetails();
			if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().getRole().equals(Role.SUPER_ADMIN)) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You are not authorized to register a clinic.",
						null);
			}
			if (registrationDto.getId() != null && registrationDto.getClinicDto().getId() == null) {
			    return new Response<>(HttpStatus.BAD_REQUEST.value(),
			            "Clinic ID is required when updating an existing admin.", null);
			}

			User loggedUser = loggedUserOptional.get();

			// New user duplicate email check
			if (registrationDto.getId() == null) {
				Optional<User> usersWithEmail = userRepository.findByEmail(registrationDto.getEmail());
				if (!usersWithEmail.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Admin user already exists.", null);
				}
			}

			// 👉 Clinic CREATE flow
			if (registrationDto.getClinicDto().getId() == null) {

				// Duplicate clinic email check
				if (clinicRepository.findByEmail(registrationDto.getClinicEmail()).isPresent()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Clinic already exists.", null);
				}

				Clinic clinic = new Clinic();
				clinic.setName(registrationDto.getClinicDto().getName());
				clinic.setEmail(registrationDto.getClinicDto().getEmail());
				clinic.setPhone(registrationDto.getClinicDto().getPhone());
				clinic.setAddress(registrationDto.getClinicDto().getAddress());
				clinic.setIsActive(true);
				clinic.setCreatedBy(loggedUser);
				clinic.setCreatedAt(new Date());

				Clinic savedClinic = clinicRepository.save(clinic);

				User savedAdmin;

				// New Admin create
				if (registrationDto.getId() == null) {
					User admin = new User();
					admin.setName(registrationDto.getName());
					admin.setEmail(registrationDto.getEmail());
					admin.setPhone(registrationDto.getPhone());
					admin.setPassword(registrationDto.getPassword() != null ? registrationDto.getPassword()
							: passwordEncoder.encode("Rst@123"));
					admin.setRole(Role.ADMIN);
					admin.setClinic(savedClinic);
					admin.setIsActive(true);
					admin.setCreatedAt(new Date());
					admin.setCreatedBy(loggedUser);

					savedAdmin = userRepository.save(admin);
				} else {
					// 👉 Existing user update inside newly created clinic (rare case)
					Optional<User> existingUserOpt = userRepository.findById(registrationDto.getId());
					if (existingUserOpt.isEmpty()) {
						return new Response<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
					}

					User user = existingUserOpt.get();

					// Clinic mismatch is not possible here as clinic is new and assigned below
					user.setClinic(savedClinic);

					if (registrationDto.getName() != null)
						user.setName(registrationDto.getName());
					if (registrationDto.getEmail() != null)
						user.setEmail(registrationDto.getEmail());
					if (registrationDto.getPhone() != null)
						user.setPhone(registrationDto.getPhone());
					if (registrationDto.getIsActive() != null)
						user.setIsActive(registrationDto.getIsActive());

					savedAdmin = userRepository.save(user);

					return new Response<>(HttpStatus.OK.value(), "Admin updated successfully.", savedAdmin);
				}

				// Assign Trial Plan
				Optional<SubscriptionPlan> trialPlan = planRepository.findByName(PlanType.TRIAL);
				if (trialPlan.isEmpty()) {
					return new Response<>(HttpStatus.OK.value(), "Trial plan not found", null);
				}

				Subscription subscription = new Subscription();
				subscription.setClinic(savedClinic);
				subscription.setPlan(trialPlan.get());
				subscription.setStartDate(new Date());
				subscription.setEndDate(Date.from(LocalDate.now().plusDays(trialPlan.get().getDurationDays())
						.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				subscription.setIsActive(true);
				subscription.setPaymentStatus("FREE");
				subscription.setReminderSent(false);

				subscriptionRepository.save(subscription);

				// 👉 Send password reset email to new admin
				emailService.sendPasswordResetEmail(savedAdmin);

				return new Response<>(HttpStatus.OK.value(), "Clinic and Admin registered successfully.", null);
			}

			// Clinic UPDATE flow
			else {
				Optional<Clinic> clinicOptional = clinicRepository.findById(registrationDto.getClinicDto().getId());
				if (clinicOptional.isEmpty()) {
					return new Response<>(HttpStatus.NOT_FOUND.value(), "Clinic not found.", null);
				}

				Clinic clinic = clinicOptional.get();

				if (registrationDto.getClinicDto().getName() != null)
					clinic.setName(registrationDto.getClinicDto().getName());
				if (registrationDto.getClinicDto().getEmail() != null)
					clinic.setEmail(registrationDto.getClinicDto().getEmail());
				if (registrationDto.getClinicDto().getPhone() != null)
					clinic.setPhone(registrationDto.getClinicDto().getPhone());
				if (registrationDto.getClinicDto().getAddress() != null)
					clinic.setAddress(registrationDto.getClinicDto().getAddress());

				Clinic updatedClinic = clinicRepository.save(clinic);

				// Also update admin if user ID provided
				if (registrationDto.getId() != null) {
					Optional<User> userOptional = userRepository.findById(registrationDto.getId());
					if (userOptional.isEmpty()) {
						return new Response<>(HttpStatus.NOT_FOUND.value(), "User not found.", null);
					}

					User user = userOptional.get();

					// Check that user belongs to this clinic
					if (!user.getClinic().getId().equals(clinic.getId())) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "User is not part of the given clinic.",
								null);
					}

					if (registrationDto.getName() != null)
						user.setName(registrationDto.getName());
					if (registrationDto.getEmail() != null)
						user.setEmail(registrationDto.getEmail());
					if (registrationDto.getPhone() != null)
						user.setPhone(registrationDto.getPhone());
					if (registrationDto.getIsActive() != null)
						user.setIsActive(registrationDto.getIsActive());

					User updatedUser = userRepository.save(user);

					return new Response<>(HttpStatus.OK.value(), "Clinic and Admin updated successfully.", null);
				}

				return new Response<>(HttpStatus.OK.value(), "Clinic updated successfully.", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	public Response<?> registerStaff(DoctorDto registrationDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loggedInEmail = authentication.getName();

			Optional<User> optionalUser = userRepository.findByEmail(loggedInEmail);
			if (optionalUser.isEmpty()) {
				return new Response<>(HttpStatus.UNAUTHORIZED.value(), "User not found", null);
			}

			User loggedInUser = optionalUser.get();
			boolean isAdmin = loggedInUser.getRole().equals(Role.ADMIN);
			boolean isReceptionistCreatingDoctor = loggedInUser.getRole().equals(Role.RECEPTIONIST)
					&& registrationDto.getRole().equals(Role.DOCTOR);

			if (!isAdmin && !isReceptionistCreatingDoctor) {
				return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Not authorized", null);
			}

			Long clinicId = loggedInUser.getClinic().getId();
			if (!subscriptionService.isClinicSubscriptionActive(clinicId)) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Subscription expired", null);
			}

			User user;

			// === Update Logic ===
			if (registrationDto.getId() != null) {
				Optional<User> optionalExistingUser = userRepository.findById(registrationDto.getId());
				if (optionalExistingUser.isEmpty()) {
					return new Response<>(HttpStatus.NOT_FOUND.value(), "User not found", null);
				}

				user = optionalExistingUser.get();

				if (registrationDto.getName() != null)
					user.setName(registrationDto.getName());
				if (registrationDto.getEmail() != null)
					user.setEmail(registrationDto.getEmail());
				if (registrationDto.getPassword() != null && !registrationDto.getPassword().isBlank())
					user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
				if (registrationDto.getPhone() != null)
					user.setPhone(registrationDto.getPhone());
				if (registrationDto.getQualification() != null)
					user.setQualification(registrationDto.getQualification());
				if (registrationDto.getYearsOfExperience() != null)
					user.setYearsOfExperience(registrationDto.getYearsOfExperience());
				if (registrationDto.getGender() != null)
					user.setGender(registrationDto.getGender());
				if (registrationDto.getProfilePic() != null)
					user.setLogoUrl(registrationDto.getProfilePic());

				user.setIsActive(true);
				user.setUpdatedAt(new Date());

			} else {
				// === Create Logic ===
				Optional<User> existingUser = userRepository.findByEmail(registrationDto.getEmail());
				if (existingUser.isPresent()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email already exists", null);
				}

				user = new User();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
				user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
				user.setPhone(registrationDto.getPhone());
				user.setRole(registrationDto.getRole());
				user.setQualification(registrationDto.getQualification());
				user.setYearsOfExperience(registrationDto.getYearsOfExperience());
				user.setGender(registrationDto.getGender());
				user.setLogoUrl(registrationDto.getProfilePic());
				user.setCreatedBy(loggedInUser);
				user.setCreatedAt(new Date());
				user.setIsActive(true);
				user.setClinic(loggedInUser.getClinic());
			}

			// === Set Department if Role is Doctor ===
			if (registrationDto.getRole().equals(Role.DOCTOR)) {
				Optional<Department> optionalDept = departmentRepository
						.findByIdAndClinic(registrationDto.getDepartmentId(), loggedInUser.getClinic());

				if (optionalDept.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "Department doesn't exist", null);
				}

				user.setDepartment(optionalDept.get());
			}

			userRepository.save(user);
			String message = (registrationDto.getId() != null) ? "User updated successfully"
					: "Registration successful";
			return new Response<>(HttpStatus.OK.value(), message, null);

		} catch (Exception e) {
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
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SUPER_ADMIN"))) {

			Optional<User> loggedInuser = customizedUserDetailsService.getUserDetails();
			if (loggedInuser.isEmpty()) {
				return new Response<>(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null);
			}
//				if (!subscriptionService.isClinicSubscriptionActive(loggedInuser.get().getClinic().getId())) {
//		            return new Response<>(HttpStatus.BAD_REQUEST.value(),"Subscrption expired",null);
//		        }
			List<User> listOfDoctors = userRepository.findAll();
			if (listOfDoctors.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "Data not found", null);
			}
			List<UserDto> doctors = listOfDoctors.stream().filter(r -> r.getRole().equals(Role.DOCTOR))
					.map(User::convertToDto).toList();
			return new Response<>(HttpStatus.OK.value(), "Success", doctors);

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