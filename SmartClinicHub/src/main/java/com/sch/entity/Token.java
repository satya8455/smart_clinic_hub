package com.sch.entity;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sch.enums.TokenStatus;

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

    private Integer tokenNumber;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    private String problemDescription;

    private LocalTime estimatedTime;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
