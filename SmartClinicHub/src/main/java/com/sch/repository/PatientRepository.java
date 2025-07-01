package com.sch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.Clinic;
import com.sch.entity.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

//	void findByClinic(Clinic clinic);

	List<Patient> findAllByClinic(Clinic clinic);

}
