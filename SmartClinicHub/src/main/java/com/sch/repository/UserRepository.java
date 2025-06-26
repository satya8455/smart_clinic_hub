package com.sch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.Clinic;
import com.sch.entity.User;
import com.sch.enums.Role;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String username);

	Optional<User> findByRoleAndClinic(Role role, Clinic clinic);

}
	