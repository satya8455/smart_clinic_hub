package com.sch.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;


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
@Column(name="is_active")
    private Boolean isActive;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @ManyToOne
	@JoinColumn(name="create_by")
	private User  createdBy;
    @ManyToOne
	@JoinColumn(name="update_by")
	private User  updatedBy;

    // Getters and Setters
}
