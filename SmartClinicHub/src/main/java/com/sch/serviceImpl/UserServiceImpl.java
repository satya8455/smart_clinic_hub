package com.sch.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sch.dto.RegistrationDto;
import com.sch.dto.Response;
import com.sch.entity.User;
import com.sch.enums.Role;
import com.sch.repository.UserRepository;
import com.sch.service.UserService;
@Service
public class UserServiceImpl implements UserService{
@Autowired
UserRepository userRepository;
	@Override
	public Response<?> registerSuperadmin(RegistrationDto registrationDto) {
		try {
			if(registrationDto.getId()==null) {
				User user=new User();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
				user.setPassword(registrationDto.getPassword());
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
				user.setCreatedAt(null);
				user.setIsActive(true);
				userRepository.save(user);
				return new Response<>(HttpStatus.OK.value(),"Registration successfully",null);
			}
			Optional<User> optionalUser=userRepository.findById(registrationDto.getId());
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setName(registrationDto.getName());
				user.setEmail(registrationDto.getEmail());
				user.setPassword(registrationDto.getPassword());
				user.setPhone(registrationDto.getPhone());
				user.setRole(Role.SUPER_ADMIN);
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

}
