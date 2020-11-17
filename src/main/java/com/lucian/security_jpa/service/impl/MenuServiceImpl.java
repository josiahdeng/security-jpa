package com.lucian.security_jpa.service.impl;

import com.lucian.security_jpa.dao.MenuDao;
import com.lucian.security_jpa.entity.Menu;
import com.lucian.security_jpa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    public List<Menu> getAllMenus(){
        return menuDao.findAll();
    }
}
