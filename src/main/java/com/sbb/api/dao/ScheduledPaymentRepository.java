package com.sbb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.ScheduledPayment;

public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment,Long> {

}
