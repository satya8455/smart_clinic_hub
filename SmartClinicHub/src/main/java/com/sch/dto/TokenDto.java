package com.sch.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.sch.enums.TokenStatus;



public class TokenDto {
	private Long id;

	private Long clinicId;

	private Long departmentId;

	private Long doctorId;

	private Long patientId;

	private String tokenNumber;

	private TokenStatus status;

	private Date createdAt;

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

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public TokenDto(Long id, Long clinicId, Long departmentId, Long doctorId, Long patientId, String tokenNumber,
			TokenStatus status, Date createdAt) {
		super();
		this.id = id;
		this.clinicId = clinicId;
		this.departmentId = departmentId;
		this.doctorId = doctorId;
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
