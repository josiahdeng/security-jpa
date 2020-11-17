package com.lucian.security_jpa.dao;

import com.lucian.security_jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {
}
