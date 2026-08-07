package com.sbb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
