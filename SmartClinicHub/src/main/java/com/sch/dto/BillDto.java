package com.sch.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sch.enums.PaymentMode;


public class BillDto {
	private Long id;

	private Long token;

	private Long patient;

	private BigDecimal totalAmount;

	private Boolean paid;

	private PaymentMode paymentMode = PaymentMode.PENDING;

	private LocalDateTime createdAt;

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

	public Long getPatient() {
		return patient;
	}

	public void setPatient(Long patient) {
		this.patient = patient;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public BillDto(Long id, Long token, Long patient, BigDecimal totalAmount, Boolean paid, PaymentMode paymentMode,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.token = token;
		this.patient = patient;
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
