package com.sch.dto;

import java.time.LocalDateTime;
import java.util.Date;


public class DepartmentDto {
	private Long id;
	
    private Long clinic;

    private String name;

    private Date createdAt;

	private Long  createdBy;
   
	private Long  updatedBy;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public DepartmentDto(Long id, Long clinic, String name, Date createdAt, Long createdBy, Long updatedBy) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.name = name;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public DepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
