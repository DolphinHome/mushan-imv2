package com.mushan.im.controller;

import com.mushan.im.annotaion.LoginUser;
import com.mushan.im.pojo.Apply;
import com.mushan.im.pojo.Friend;
import com.mushan.im.pojo.User;
import com.mushan.im.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    //获取好友列表
    @GetMapping("/list")
    public Object list(@LoginUser User user){
        return friendService.list(user);
    }


    //添加好友
    @PostMapping("/add")
    public Object add(@LoginUser User user, @RequestBody Apply apply){
        apply.setUid(user.getId());
        return friendService.add(apply);
    }



    @GetMapping("/getFriends")
    public Object getFriends(@LoginUser User user){
        return friendService.getFriends(user);
    }
    //同意
    @PostMapping("/agree")
    public Object agree(@LoginUser User user, @RequestBody Friend friend){
        friend.setUid(user.getId());
        return friendService.agree(friend);
    }


}
