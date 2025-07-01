package com.sch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sch.entity.Clinic;
import com.sch.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

//	List<Department> findAllByClinicId(Long clinicId);

	@Query("SELECT d FROM Department d WHERE LOWER(d.name) = LOWER(:name) AND d.clinic = :clinic")
	Optional<Department> findByNameIgnoreCaseAndClinic(@Param("name") String name, @Param("clinic") Clinic clinic);

//	Optional<Department> findByIdAndClinicId(Long id, Long id2);
	Optional<Department> findByIdAndClinic(Long departmentId, Clinic clinic);


//	Optional<Department> findByIdAndClinicId(Long departmentId, Long clinicId);

	List<Department> findAllByClinic(Clinic clinic);

	Optional<Department> findByClinicAndId(Clinic clinic, Long deptId);
}