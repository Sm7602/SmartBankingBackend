package com.sbb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

}
