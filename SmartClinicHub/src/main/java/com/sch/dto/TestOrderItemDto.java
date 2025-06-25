package com.sch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestOrderItemDto {
	private Long id;
	
	private Long token;

	private String testName;
	
	private BigDecimal testPrice;
	
	private Boolean isCompleted;

	private Long addedBy;

	private LocalDateTime addedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
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

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public TestOrderItemDto(Long id, Long token, String testName, BigDecimal testPrice, Boolean isCompleted,
			Long addedBy, LocalDateTime addedAt) {
		super();
		this.id = id;
		this.token = token;
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
