package com.lucian.security_jpa.dao;

import com.lucian.security_jpa.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDao extends JpaRepository<Menu, Integer> {
}
