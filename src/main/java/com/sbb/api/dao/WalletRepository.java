package com.sbb.api.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbb.api.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

	@Query("""
	        SELECT COALESCE(SUM(t.amount), 0)
	        FROM Transaction t
	        WHERE t.wallet.id = :id
	        AND t.transactionType = 'TRANSFER'
	        AND t.status = 'SUCCESS'
	        AND t.transactionTime >= :startOfDay
	        AND t.transactionTime < :endOfDay
	        """)
	BigDecimal getTodayTransferAmount(
	        @Param("walletId") Long walletId,
	        @Param("startOfDay") LocalDateTime startOfDay,
	        @Param("endOfDay") LocalDateTime endOfDay);

}
