package com.sch.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.sch.dto.SubscriptionPlanDto;
import com.sch.enums.PlanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private PlanType name;

	private BigDecimal price;

	@Column(name = "duration_days")
	private Integer durationDays;
	@Column(name = "discount_percent")

	private Integer discountPercent = 0;
	@Column(name = "created_at")
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlanType getName() {
		return name;
	}

	public void setName(PlanType name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(Integer durationDays) {
		this.durationDays = durationDays;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public SubscriptionPlan(Long id, PlanType name, BigDecimal price, Integer durationDays, Integer discountPercent,
			Date createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.durationDays = durationDays;
		this.discountPercent = discountPercent;
		this.createdAt = createdAt;
	}

	public SubscriptionPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubscriptionPlanDto convertToDto() {
		return new SubscriptionPlanDto(this.id!=null?this.id:null,this.name!=null?this.name:null,this.price!=null?this.price:null,this.durationDays!=null?this.durationDays:null,this.discountPercent!=null?this.discountPercent:null);
	}

}
