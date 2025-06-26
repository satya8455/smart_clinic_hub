package com.sch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.sch.enums.PaymentMode;


public class BillDto {
	private Long id;

	private Long tokenId;

	private Long patientId;

	private BigDecimal totalAmount;

	private Boolean paid;

	private PaymentMode paymentMode = PaymentMode.PENDING;

	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public BillDto(Long id, Long tokenId, Long patientId, BigDecimal totalAmount, Boolean paid, PaymentMode paymentMode,
			Date createdAt) {
		super();
		this.id = id;
		this.tokenId = tokenId;
		this.patientId = patientId;
		this.totalAmount = totalAmount;
		this.paid = paid;
		this.paymentMode = paymentMode;
		this.createdAt = createdAt;
	}

	public BillDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
