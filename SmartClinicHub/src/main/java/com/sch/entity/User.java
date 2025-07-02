package com.sch.entity;

import java.time.LocalTime;
import java.util.Date;

import com.sch.dto.UserDto;
import com.sch.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	private String phone;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "is_active")
	private Boolean isActive;

	private LocalTime availableFrom;
	private LocalTime availableTo;

	@Column(name = "created_at")
	private Date createdAt;
	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "update_by")
	private User updatedBy;
	@Column(name = "updated_at")
	private Date updatedAt;

	private String qualification;

	@Column(name = "years_of_experience")
	private Long yearsOfExperience;

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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Long getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Long yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public User(Long id, String name, String email, String phone, String password, Role role, Clinic clinic,
			Department department, Boolean isActive, LocalTime availableFrom, LocalTime availableTo, Date createdAt,
			User createdBy, User updatedBy, Date updatedAt) {
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
		this.updatedAt = updatedAt;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto convertToDto() {
		return new UserDto(this.id != null ? this.id : null, this.name != null ? this.name : null,
				this.email != null ? this.email : null, this.phone != null ? this.phone : null,
				this.password != null ? this.password : null, this.role != null ? this.role : null,
				this.clinic != null ? this.clinic.getId() : null,
				this.department != null ? this.department.getId() : null, this.isActive != null ? this.isActive : null,
				this.availableFrom != null ? this.availableFrom : null,
				this.availableTo != null ? this.availableTo : null, this.createdAt != null ? this.createdAt : null,
				this.createdBy != null ? this.createdBy.getId() : null,
				this.updatedBy != null ? this.updatedBy.getId() : null, this.updatedAt != null ? this.updatedAt : null);
	}

}
