package com.sch.dto;

import java.time.LocalDateTime;

import com.sch.enums.TokenStatus;



public class TokenDto {
	private Long id;

	private Long clinicId;

	private Long departmentId;

	private Long doctor;

	private Long patientId;

	private Integer tokenNumber;

	private TokenStatus status;

	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDoctor() {
		return doctor;
	}

	public void setDoctor(Long doctor) {
		this.doctor = doctor;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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

	public TokenDto(Long id, Long clinicId, Long departmentId, Long doctor, Long patientId, Integer tokenNumber,
			TokenStatus status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.clinicId = clinicId;
		this.departmentId = departmentId;
		this.doctor = doctor;
		this.patientId = patientId;
		this.tokenNumber = tokenNumber;
		this.status = status;
		this.createdAt = createdAt;
	}

	public TokenDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
