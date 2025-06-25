package com.sch.dto;

import java.time.LocalDateTime;

import com.sch.entity.Clinic;
import com.sch.enums.Gender;


public class PatientDto {
	private Long id;
	
	private Long clinic;

	private String name;

	private String phone;

	private Integer age;

	private Gender gender;

	private String languagePref;
	
	private String relationType;
	
	private String relationName;
	
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public PatientDto(Long id, Long clinic, String name, String phone, Integer age, Gender gender,
			String languagePref, String relationType, String relationName, LocalDateTime createdAt) {
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

	public PatientDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
