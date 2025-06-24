package com.sch.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sch.enums.PaymentMode;

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

    private BigDecimal totalAmount;

    private Boolean paid = false;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode = PaymentMode.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
