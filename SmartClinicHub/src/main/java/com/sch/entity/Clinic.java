package com.sch.entity;

import java.util.Date;

import com.sch.dto.ClinicDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clinics")
public class Clinic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String address;

	private String phone;

	private String subdomain;
	private String email;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "is_active")
	private Boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Clinic(Long id, String name, String address, String phone, String subdomain, String email, User createdBy,
			Date createdAt, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.subdomain = subdomain;
		this.email = email;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isActive = isActive;
	}

	public Clinic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicDto convertToDTo() {
		return new ClinicDto(this.id!=null?this.id:null,
			this.name!=null?this.name:null,
			this.address!=null?this.address:null,
			this.phone!=null?this.phone:null,
					this.email!=null?this.email:null,
			this.subdomain!=null?this.subdomain:null,
			this.createdBy!=null?this.createdBy.getId():null,
			this.createdAt!=null?this.createdAt:null);
	}
}
