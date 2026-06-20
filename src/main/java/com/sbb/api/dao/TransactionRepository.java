package com.sbb.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

	List<Transaction> findByAccountAccountNumber(String accountNumber);

}
