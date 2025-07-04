package com.sch.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.sch.dto.BillDto;
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
	private PaymentMode paymentMode;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name="payment_date")
	private Date paymentDate;
	@Column(name="discount_amount")
	private Long discountAmount;
	@Column(name="coupon_code")
	private String couponCode;
	
	
	
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Bill(Long id, Token token, Patient patient, BigDecimal totalAmount, Boolean paid, PaymentMode paymentMode,
			Date createdAt) {
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

	public BillDto convertToDTo() {
		return new BillDto(this.id!=null?this.id:null,
			this.token!=null?this.token.getId():null,
			this.patient!=null?this.patient.getId():null,
			this.totalAmount!=null?this.totalAmount:null,
			this.paid!=null?this.paid:null,
			this.paymentMode!=null?this.paymentMode:null,
			this.createdAt!=null?this.createdAt:null);
	}
	
}
