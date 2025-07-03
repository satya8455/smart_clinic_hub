package com.sch.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.sch.dto.TestOrderItemDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_order_items")
public class TestOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "token_id")
	private Token token;

	@Column(name = "test_name")
	private String testName;
	
	@Column(name = "test_price")
	private BigDecimal testPrice;
	
	@Column(name = "is_completed")
	private Boolean isCompleted;

	@ManyToOne
	@JoinColumn(name = "added_by")
	private User addedBy;
	
	@Column(name = "added_at")
	private Date addedAt;

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

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	public TestOrderItem(Long id, Token token, String testName, BigDecimal testPrice, Boolean isCompleted, User addedBy,
			Date addedAt) {
		super();
		this.id = id;
		this.token = token;
		this.testName = testName;
		this.testPrice = testPrice;
		this.isCompleted = isCompleted;
		this.addedBy = addedBy;
		this.addedAt = addedAt;
	}

	public TestOrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestOrderItemDto convertToDto() {
		return new TestOrderItemDto(this.id!=null?this.id:null,
				this.token!=null?this.token.getId():null,
				this.testName!=null?this.testName:null,
				this.testPrice!=null?this.testPrice:null,
				this.isCompleted!=null?this.isCompleted:null,
				this.addedBy!=null?this.addedBy.getId():null,
				this.addedAt!=null?this.addedAt:null);
	}
}
