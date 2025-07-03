package com.sch.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Linked clinic
	@ManyToOne
	@JoinColumn(name = "clinic_id", nullable = false)
	private Clinic clinic;

	// Linked plan
	@ManyToOne
	@JoinColumn(name = "plan_id", nullable = false)
	private SubscriptionPlan plan;

	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")

	private Date endDate;
	@Column(name = "is_active")

	private Boolean isActive;

	@Column(name = "payment_status")
	private String paymentStatus;
	@Column(name = "reminder_sent")
	private Boolean reminderSent;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	public SubscriptionPlan getPlan() {
		return plan;
	}
	public void setPlan(SubscriptionPlan plan) {
		this.plan = plan;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Boolean getReminderSent() {
		return reminderSent;
	}
	public void setReminderSent(Boolean reminderSent) {
		this.reminderSent = reminderSent;
	}
	public Subscription(Long id, Clinic clinic, SubscriptionPlan plan, Date startDate, Date endDate, Boolean isActive,
			String paymentStatus, Boolean reminderSent) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.plan = plan;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.paymentStatus = paymentStatus;
		this.reminderSent = reminderSent;
	}
	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
	
	
	
}
