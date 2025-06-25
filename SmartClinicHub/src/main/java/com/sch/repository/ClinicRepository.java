package com.sch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.Clinic;
@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

	Optional<Clinic> findByEmail(String email);

}
