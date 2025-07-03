package com.sch.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sch.config.CustomizedUserDetailsService;
import com.sch.dto.ClinicDto;
import com.sch.dto.Response;
import com.sch.entity.Clinic;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.ClinicRepository;
import com.sch.repository.UserRepository;
import com.sch.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {
	@Autowired
	ClinicRepository clinicRepository;

	@Autowired
	private CustomizedUserDetailsService customizedUserDetailsService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Response<?> getClinicById(Long id) {
		try {
			Optional<Clinic> optionalClinic = clinicRepository.findById(id);
			if (optionalClinic.isPresent()) {
				return new Response<>(HttpStatus.OK.value(), "Data retrieved successfully", optionalClinic.get());
			}
			return new Response<>(HttpStatus.OK.value(), "Data not found", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

	@Override
	public Response<?> getAllClinic() {
		try {
			Optional<User> loggedInUser = customizedUserDetailsService.getUserDetails();
			if (loggedInUser.isEmpty() || !loggedInUser.get().getRole().equals(Role.SUPER_ADMIN)) {
				return new Response<>(HttpStatus.UNAUTHORIZED.value(), "You are not authorized to view clinics", null);
			}

			List<Clinic> clinics = clinicRepository.findAll();

			if (clinics.isEmpty()) {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "No clinics found", null);
			}

			List<ClinicDto> clinicDtos = clinics.stream().map(clinic -> {
				ClinicDto dto = clinic.convertToDTo();

				User admin = userRepository.findByClinicIdAndRole(clinic.getId(), Role.ADMIN);
				if (admin != null) {
					dto.setAdmin(admin.convertToDto());
				}

				return dto;
			}).toList();

			return new Response<>(HttpStatus.OK.value(), "Clinics retrieved successfully", clinicDtos);

		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", null);
		}
	}

}
