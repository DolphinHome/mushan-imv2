package com.mushan.im.service;

import com.mushan.im.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Object getUserByUserName(String username) {
       return userDao.getUserByUserName(username);
    }
}
