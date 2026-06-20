package com.sbb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbb.api.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
