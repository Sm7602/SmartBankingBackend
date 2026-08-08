package com.sbb.api.dto.account;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountUpdateRequest {
	

    private String accountType;

    private BigDecimal balance;

    private BigDecimal minimumBalance;

    private BigDecimal dailyTransferLimit;

    private BigDecimal withdrawalLimit;

    private Boolean active;

    private String branchName;

    private String ifscCode;

}
