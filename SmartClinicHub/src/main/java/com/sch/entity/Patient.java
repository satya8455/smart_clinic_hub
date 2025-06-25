package com.sch.entity;

import java.time.LocalDateTime;

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
	private LocalDateTime createdAt;

	// Getters and Setters
}
