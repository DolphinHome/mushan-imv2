package com.mushan.im.service;

import com.mushan.im.dao.ApplyDao;
import com.mushan.im.dao.FriendDao;
import com.mushan.im.pojo.Apply;
import com.mushan.im.pojo.Friend;
import com.mushan.im.pojo.User;
import com.mushan.im.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private ApplyDao applyDao;

    public Object list(User user) {
       List<User> list =  friendDao.list(user.getId());
       return ResponseUtils.ok(list);
    }


    public Object add(Apply apply) {
        applyDao.add(apply);
        return ResponseUtils.ok();
}

    public Object getFriends(User user) {
       List<User> list =  applyDao.getFriends(user.getId());
       return ResponseUtils.ok(list);
    }


    public Object agree(Friend friend) {
        friendDao.add(friend);
        Friend friend1 = new Friend();
        friend1.setUid(friend.getFid());
        friend1.setFid(friend.getUid());
        friendDao.add(friend1);
        return ResponseUtils.ok();
    }
}
