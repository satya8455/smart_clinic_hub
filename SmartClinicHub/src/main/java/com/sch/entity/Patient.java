package com.sch.entity;

import java.util.Date;

import com.sch.dto.PatientDto;
import com.sch.enums.Gender;

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
@Table(name = "patients")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;

	private String name;

	private String phone;

	private Integer age;
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "language_pref")
	private String languagePref;
	
	@Column(name = "relation_type")
	private String relationType;
	
	@Column(name="relation_name")
	private String relationName;
	@Column(name = "created_at")
	private Date createdAt;
	public Patient(Long id, Clinic clinic, String name, String phone, Integer age, Gender gender, String languagePref,
			String relationType, String relationName, Date createdAt) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.name = name;
		this.phone = phone;
		this.age = age;
		this.gender = gender;
		this.languagePref = languagePref;
		this.relationType = relationType;
		this.relationName = relationName;
		this.createdAt = createdAt;
	}
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getLanguagePref() {
		return languagePref;
	}
	public void setLanguagePref(String languagePref) {
		this.languagePref = languagePref;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

public PatientDto convertToDto() {	
	return new PatientDto(this.id!=null?this.id:null,
			this.clinic!=null?this.clinic.getId():null,
			this.name!=null?this.name:null,
			this.phone!=null?this.phone:null,
			this.age!=null?this.age:null,
			this.gender!=null?this.gender:null,
			this.languagePref!=null?this.languagePref:null,
			this.relationType!=null?this.relationType:null,
			this.relationName!=null?this.relationName:null,
			this.createdAt!=null?this.createdAt:null);
}
	
}
