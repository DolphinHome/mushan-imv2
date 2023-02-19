package com.mushan.im.dao;

import com.mushan.im.pojo.Friend;
import com.mushan.im.pojo.User;

import java.util.List;
import java.util.Map;

public interface FriendDao {
    List<User> list(Long id);

    int add(Friend friend);
}
