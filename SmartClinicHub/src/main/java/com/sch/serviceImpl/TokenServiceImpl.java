package com.sch.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;
import com.sch.entity.Department;
import com.sch.entity.Patient;
import com.sch.entity.Token;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.enums.TokenStatus;
import com.sch.repository.DepartmentRepository;
import com.sch.repository.PatientRepository;
import com.sch.repository.TokenRepository;
import com.sch.repository.UserRepository;
import com.sch.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	TokenRepository tokenRepository;
	@Override
	public Response<?> createToken(TokenDto tokenDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getAuthorities().stream()
					.anyMatch(auth ->auth.getAuthority().equals("RECEPTIONIST"))) {
				Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
				if (optionalUser.isEmpty()) {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
				}
				User user = optionalUser.get();
				String tokenNo="SCH"+user.getId();
				if (tokenDto.getId() == null) {
					Token token = new Token();
					token.setCreatedAt(new Date());
					token.setTokenNumber(tokenNo);
					token.setClinic(user.getClinic());
					token.setStatus(tokenDto.getStatus());

					Optional<Department> optionalDept = departmentRepository.findByIdAndClinic(tokenDto.getDepartmentId(), user.getClinic());
					if(optionalDept.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(),"Department not found",null);
					}
					
					token.setDepartment(optionalDept.get());
					Optional<User> optionalDoctor = userRepository.findByIdAndClinic(tokenDto.getDoctorId(),user.getClinic());
					User doctor = optionalDoctor.get();
					if(optionalDoctor.isEmpty()||!doctor.getRole().equals(Role.DOCTOR)||doctor.getRole().equals(Role.SUPER_ADMIN)||
							doctor.getRole().equals(Role.ADMIN)||doctor.getRole().equals(Role.RECEPTIONIST)) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(),"Doctor not found",null);
					}
					System.out.println(doctor.getName());
					System.out.println(user.getClinic().getId());
					token.setDoctor(doctor);
					Optional<Patient> optionalPatient = patientRepository.findById(tokenDto.getPatientId());
					if(optionalPatient.isEmpty()) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(),"Patient not found",null);
					}
					token.setPatient(optionalPatient.get());
					tokenRepository.save(token);
					return new Response<>(HttpStatus.OK.value(),"Token created successfully",null);
				}
			}
			return new Response<>(HttpStatus.UNAUTHORIZED.value(),"Not authorized",null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null);

		}
	
	}

}
