package com.sch.dto;

import java.math.BigDecimal;

import com.sch.enums.PlanType;

public class SubscriptionPlanDto {
	
	private Long id;
	
	
    private PlanType name; 
    private BigDecimal price;
    private Integer durationDays;
    private Integer discountPercent;
    
    
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
	public SubscriptionPlanDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public SubscriptionPlanDto(Long id,PlanType name, BigDecimal price, Integer durationDays,
			Integer discountPercent) {
		super();
		this.id = id;
		
		this.name = name;
		this.price = price;
		this.durationDays = durationDays;
		this.discountPercent = discountPercent;
	}
	
	
    
    
}
