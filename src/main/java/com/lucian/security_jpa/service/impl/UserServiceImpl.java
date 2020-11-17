package com.lucian.security_jpa.service.impl;

import com.lucian.security_jpa.entity.User;
import com.lucian.security_jpa.dao.UserDao;
import com.lucian.security_jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        if (user == null) throw new UsernameNotFoundException("用户不存在");
        return user;
    }
}
