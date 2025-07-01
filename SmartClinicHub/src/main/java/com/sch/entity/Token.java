package com.sch.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import com.sch.dto.TokenDto;
import com.sch.enums.TokenStatus;

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
@Table(name = "tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private User doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(name = "token_number")
	private String tokenNumber;

	@Enumerated(EnumType.STRING)
	private TokenStatus status;

	@Column(name = "created_at")
	private Date createdAt;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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

	public Token(Long id, Clinic clinic, Department department, User doctor, Patient patient, String tokenNumber,
			TokenStatus status, Date createdAt) {
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

	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TokenDto convertToDto() {
		return new TokenDto(this.id!=null?this.id:null,
			this.clinic!=null?this.clinic.getId():null,
			this.department!=null?this.department.getId():null,
			this.doctor!=null?this.doctor.getId():null,
			this.patient!=null?this.patient.getId():null,
			this.tokenNumber!=null?this.tokenNumber:null,
			this.status!=null?this.status:null,
			this.createdAt!=null?this.createdAt:null);
	}
	
}
