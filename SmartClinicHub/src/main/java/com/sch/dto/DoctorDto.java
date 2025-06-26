package com.sch.dto;

import java.time.LocalTime;
import java.util.Date;

import com.sch.enums.Role;

public class DoctorDto  extends RegistrationDto{

	
    private String profilePic;
    
    private Date birthDay;
    
    private String gender;
    
    private String qualification;
    
    private Long departmentId;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public DoctorDto(Long id, String name, String email, String phone, String password, Role role, Long clinicId,
			Long departmentId, Boolean isActive, LocalTime availableFrom, LocalTime availableTo, Date createdAt,
			Long createdBy, Long updatedBy, String profilePic, Date birthDay, String gender, String qualification,
			Long departmentId2) {
		super(id, name, email, phone, password, role, clinicId, departmentId, isActive, availableFrom, availableTo,
				createdAt, createdBy, updatedBy);
		this.profilePic = profilePic;
		this.birthDay = birthDay;
		this.gender = gender;
		this.qualification = qualification;
		departmentId = departmentId2;
	}

	public DoctorDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DoctorDto(Long id, String name, String email, String phone, String password, Role role, Long clinicId,
			Long departmentId, Boolean isActive, LocalTime availableFrom, LocalTime availableTo, Date createdAt,
			Long createdBy, Long updatedBy) {
		super(id, name, email, phone, password, role, clinicId, departmentId, isActive, availableFrom, availableTo, createdAt,
				createdBy, updatedBy);
		// TODO Auto-generated constructor stub
	}
    
    
    

}
