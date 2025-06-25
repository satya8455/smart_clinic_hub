package com.sch.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sch.enums.Role;

public class RegistrationDto {
	private Long id;

    private String name;

    private String email;

    private String phone;

    private String password;

    private Role role;

    private Long clinic;

    private Long department;

    private Boolean isActive;

    private LocalTime availableFrom;
    
    private LocalTime availableTo;

    private LocalDateTime createdAt;
 
	private Long  createdBy;
 
	private Long  updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getClinic() {
		return clinic;
	}

	public void setClinic(Long clinic) {
		this.clinic = clinic;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalTime getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(LocalTime availableFrom) {
		this.availableFrom = availableFrom;
	}

	public LocalTime getAvailableTo() {
		return availableTo;
	}

	public void setAvailableTo(LocalTime availableTo) {
		this.availableTo = availableTo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public RegistrationDto(Long id, String name, String email, String phone, String password, Role role, Long clinic,
			Long department, Boolean isActive, LocalTime availableFrom, LocalTime availableTo, LocalDateTime createdAt,
			Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
		this.clinic = clinic;
		this.department = department;
		this.isActive = isActive;
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public RegistrationDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
