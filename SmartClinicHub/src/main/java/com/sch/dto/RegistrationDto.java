package com.sch.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import com.sch.enums.Role;

public class RegistrationDto {
	private Long id;

    private String name;

    private String email;

    private String phone;

    private String password;
    
    private String address;

    private Role role;

    private Long departmentId;

    private Boolean isActive;

    private LocalTime availableFrom;
    
    private LocalTime availableTo;

    private Date createdAt;
 
	private Long  createdBy;
 
	private Long  updatedBy;
	
	// clinic
	private Long clinicId;
	private String clinicName;
	private String clinicEmail;
	private String clinicPhoneNo;

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


	public Long getDepartment() {
		return departmentId;
	}

	public void setDepartment(Long departmentId) {
		this.departmentId = departmentId;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
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

	


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getClinicEmail() {
		return clinicEmail;
	}

	public void setClinicEmail(String clinicEmail) {
		this.clinicEmail = clinicEmail;
	}

	public String getClinicPhoneNo() {
		return clinicPhoneNo;
	}

	public void setClinicPhoneNo(String clinicPhoneNo) {
		this.clinicPhoneNo = clinicPhoneNo;
	}

	public RegistrationDto(Long id, String name, String email, String phone, String password, Role role, Long clinicId,
			Long departmentId, Boolean isActive, LocalTime availableFrom, LocalTime availableTo, Date createdAt,
			Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
		this.clinicId = clinicId;
		this.departmentId = departmentId;
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
