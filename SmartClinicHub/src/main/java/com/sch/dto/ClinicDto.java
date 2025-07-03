package com.sch.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class ClinicDto {
	 private Long id;

	    private String name;

	    private String address;

	    private String phone;

	    private String email;
	    private String subdomain;

	    private Long createdBy;

	    private Date createdAt;
	    private UserDto admin;

	    public UserDto getAdmin() {
	        return admin;
	    }

	    public void setAdmin(UserDto admin) {
	        this.admin = admin;
	    }

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

		public Long getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Long createdBy) {
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

	
	

		public ClinicDto(Long id, String name, String address, String phone, String email, String subdomain,
				Long createdBy, Date createdAt) {
			super();
			this.id = id;
			this.name = name;
			this.address = address;
			this.phone = phone;
			this.email = email;
			this.subdomain = subdomain;
			this.createdBy = createdBy;
			this.createdAt = createdAt;
		}

		public ClinicDto() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
}
