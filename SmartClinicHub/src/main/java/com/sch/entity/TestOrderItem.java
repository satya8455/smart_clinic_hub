package com.sch.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	@Column(name="is_completed")
	private Boolean isCompleted;

	@ManyToOne
	@JoinColumn(name = "added_by")
	private User addedBy;
@Column(name="added_at")
	private LocalDateTime addedAt;

	// Getters and Setters
}
