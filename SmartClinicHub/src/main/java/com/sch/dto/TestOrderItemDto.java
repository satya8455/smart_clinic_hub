package com.sch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class TestOrderItemDto {
	private Long id;
	
	private Long tokenId;

	private String testName;
	
	private BigDecimal testPrice;
	
	private Boolean isCompleted;

	private Long addedBy;

	private Date addedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getToken() {
		return tokenId;
	}

	public void setToken(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public BigDecimal getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(BigDecimal testPrice) {
		this.testPrice = testPrice;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Long getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Long addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	public TestOrderItemDto(Long id, Long tokenId, String testName, BigDecimal testPrice, Boolean isCompleted,
			Long addedBy, Date addedAt) {
		super();
		this.id = id;
		this.tokenId = tokenId;
		this.testName = testName;
		this.testPrice = testPrice;
		this.isCompleted = isCompleted;
		this.addedBy = addedBy;
		this.addedAt = addedAt;
	}

	public TestOrderItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
