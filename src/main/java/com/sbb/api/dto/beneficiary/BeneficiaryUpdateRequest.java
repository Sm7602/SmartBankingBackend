package com.sbb.api.dto.beneficiary;

import lombok.Data;

@Data
public class BeneficiaryUpdateRequest {

	
	   private String beneficiaryName;

	    private String accountNumber;

	    private String bankName;

	    private String ifscCode;

	    private String nickname;
	    
	    private Boolean active;
}
