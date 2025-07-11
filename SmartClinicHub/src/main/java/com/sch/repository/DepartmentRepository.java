package com.sch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sch.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
