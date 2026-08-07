package com.sbb.api.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.api.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {

}
