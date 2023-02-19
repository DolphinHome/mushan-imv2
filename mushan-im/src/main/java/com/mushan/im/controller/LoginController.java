package com.mushan.im.controller;

import com.mushan.im.service.LoginService;
import com.mushan.im.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Object login(@RequestBody Map<String,String> map){
        if (CollectionUtils.isEmpty(map)){
            return ResponseUtils.badArgument();
        }
        return loginService.login(map);
    }




}
