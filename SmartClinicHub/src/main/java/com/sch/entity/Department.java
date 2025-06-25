package com.sch.entity;
import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    @ManyToOne
	@JoinColumn(name="create_by")
	private User  createdBy;
    @ManyToOne
	@JoinColumn(name="update_by")
	private User  updatedBy;
    // Getters and Setters
    
    
}
