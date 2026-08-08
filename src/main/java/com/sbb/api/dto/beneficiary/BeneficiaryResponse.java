package com.sbb.api.dto.beneficiary;

import java.time.LocalDateTime;
import com.sbb.api.entity.Customer;
import com.sbb.api.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryResponse {
	
	    private Long id;

	    private String beneficiaryName;

	    private String accountNumber;

	    private String bankName;

	    private String ifscCode;

	    private String nickname;
	    
	    private Boolean active;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    private Customer customer;
	    
	    private User user;

}
