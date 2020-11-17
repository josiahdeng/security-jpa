package com.lucian.security_jpa.dao;

import com.lucian.security_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}
