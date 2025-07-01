package com.sch.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.sch.dto.DepartmentDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;

	private String name;

	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "create_by")
	private User createdBy;
	
	@ManyToOne
	@JoinColumn(name = "update_by")
	private User updatedBy;

	private Boolean isActive;
	
	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Department(Long id, Clinic clinic, String name, Date createdAt, User createdBy, User updatedBy,Boolean isActive) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.name = name;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive=isActive;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentDto convertToDto() {
		return new DepartmentDto(this.id != null ? this.id : null, this.clinic != null ? this.clinic.getId() : null,
				this.name != null ? this.name : null, this.createdAt != null ? this.createdAt : null,
				this.createdBy != null ? this.createdBy.getId() : null,
				this.updatedBy != null ? this.updatedBy.getId() : null,
				this.isActive!=null?this.isActive:null);
	}
}
