package com.mushan.im.controller;

import com.mushan.im.annotaion.LoginUser;
import com.mushan.im.pojo.User;
import com.mushan.im.service.UserService;
import com.mushan.im.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Object User(@LoginUser User user){
        return ResponseUtils.ok(user);
    }

    @GetMapping("/getUserByUserName")
    public Object getUserByUserName(String username){
        return ResponseUtils.ok(userService.getUserByUserName(username));
    }




}
