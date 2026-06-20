package com.sbb.api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionReference;

    private String transactionType;

    private BigDecimal amount;

    private BigDecimal availableBalance;

    private String remarks;

    private String status;

    private LocalDateTime transactionTime;

   
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
