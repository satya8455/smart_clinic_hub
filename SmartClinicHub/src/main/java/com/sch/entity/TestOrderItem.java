package com.sch.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private String testName;

    private BigDecimal testPrice;

    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "added_by")
    private User addedBy;

    private LocalDateTime addedAt = LocalDateTime.now();

    // Getters and Setters
}
