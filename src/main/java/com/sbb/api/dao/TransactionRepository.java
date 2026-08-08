package com.sbb.api.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbb.api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

	List<Transaction> findByAccount_AccountNumber(String accountNumber);


    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.account.id = :accountId
        AND t.transactionType = 'TRANSFER'
        AND t.status = 'SUCCESS'
        AND t.transactionTime >= :startOfDay
        AND t.transactionTime < :endOfDay
        """)
	 BigDecimal getTodayTransferAmount(
	            @Param("accountId") Long accountId,
	            @Param("startOfDay") LocalDateTime startOfDay,
	            @Param("endOfDay") LocalDateTime endOfDay);


    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.account.id = :accountId
        AND t.transactionType = 'TRANSFER'
        AND t.status = 'SUCCESS'
        AND t.transactionTime >= :startOfDay
        AND t.transactionTime < :endOfDay
        """)
	 BigDecimal getTodayWithdrawAmount(
	            @Param("accountId") Long accountId,
	            @Param("startOfDay") LocalDateTime startOfDay,
	            @Param("endOfDay") LocalDateTime endOfDay);

}
