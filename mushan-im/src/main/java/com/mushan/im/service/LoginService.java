package com.mushan.im.service;

import com.mushan.im.dao.UserDao;
import com.mushan.im.pojo.User;
import com.mushan.im.utils.JwtUtil;
import com.mushan.im.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;

    public Object login(Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        User user = userDao.getUserByUserName(username);
        if (user == null){
            return ResponseUtils.username();
        }

        if (!user.getPassword().equals(password)){
            return ResponseUtils.username();
        }

        return ResponseUtils.ok(jwtUtil.createJWT(user));
    }
}
