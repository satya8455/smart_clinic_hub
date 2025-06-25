package com.sch.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sch.enums.PaymentMode;

import jakarta.persistence.Column;
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
@Table(name = "bills")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "token_id")
	private Token token;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	private Boolean paid;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode = PaymentMode.PENDING;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
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
	public Bill(Long id, Token token, Patient patient, BigDecimal totalAmount, Boolean paid, PaymentMode paymentMode,
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
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
	
}
