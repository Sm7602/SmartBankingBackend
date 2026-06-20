package com.sbb.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

	Optional<Account> findByAccountNumber(String accountNumber);

}
