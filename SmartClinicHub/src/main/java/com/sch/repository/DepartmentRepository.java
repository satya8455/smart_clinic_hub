package com.sch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.Clinic;
import com.sch.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

	List<Department> findAllByClinicId(Clinic clinic);

}
