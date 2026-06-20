package com.sbb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long>{

}
