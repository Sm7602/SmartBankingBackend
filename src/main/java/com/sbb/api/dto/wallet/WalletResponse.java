package com.sbb.api.dto.wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sbb.api.entity.Customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletResponse {

	private Long id;

    private String walletNumber;

    private BigDecimal walletBalance;

    private BigDecimal dailyLimit;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Customer customer;
}
