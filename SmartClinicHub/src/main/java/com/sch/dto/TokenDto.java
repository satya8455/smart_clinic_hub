package com.sch.dto;

import java.time.LocalDateTime;

import com.sch.enums.TokenStatus;



public class TokenDto {
	private Long id;

	private Long clinic;

	private Long department;

	private Long doctor;

	private Long patient;

	private Integer tokenNumber;

	private TokenStatus status;

	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getDoctor() {
		return doctor;
	}

	public void setDoctor(Long doctor) {
		this.doctor = doctor;
	}

	public Long getPatient() {
		return patient;
	}

	public void setPatient(Long patient) {
		this.patient = patient;
	}

	public Integer getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(Integer tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public TokenDto(Long id, Long clinic, Long department, Long doctor, Long patient, Integer tokenNumber,
			TokenStatus status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.department = department;
		this.doctor = doctor;
		this.patient = patient;
		this.tokenNumber = tokenNumber;
		this.status = status;
		this.createdAt = createdAt;
	}

	public TokenDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
